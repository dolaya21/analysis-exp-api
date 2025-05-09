package org.smu.controller;

import org.smu.database.entity.AnalysisResult;
import org.smu.database.entity.Post;
import org.smu.database.entity.User;
import org.smu.database.key.PostId;
import org.smu.database.key.UserId;
import org.smu.database.repository.PostRepository;
import org.smu.database.repository.RepostRepository;
import org.smu.database.repository.AnalysisResultRepository;
import org.smu.database.repository.UserRepository;
import org.smu.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.smu.util.PostUtils.mapPostsToDTOs;
import static org.smu.util.PostUtils.mapRepostsToDTOs;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RepostRepository repostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnalysisResultRepository analysisResultRepository;

    @GetMapping("/by-social-media/{name}")
    public List<PostResponseDTO> getPostsBySocialMedia(@PathVariable String name) {
        List<PostResponseDTO> results = new ArrayList<>();
        results.addAll(mapPostsToDTOs(postRepository.findBySocialMedia(name), analysisResultRepository));
        results.addAll(mapRepostsToDTOs(repostRepository.findById_SocialMedia(name)));
        return results;
    }

    @GetMapping("/between")
    public List<PostResponseDTO> getPostsBetweenTimes(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<PostResponseDTO> results = new ArrayList<>();
        results.addAll(mapPostsToDTOs(postRepository.findByTimeBetween(start, end), analysisResultRepository));
        results.addAll(mapRepostsToDTOs(repostRepository.findById_TimeBetween(start, end)));
        return results;
    }

    @GetMapping("/by-user-and-media")
    public List<PostResponseDTO> getPostsByUserAndMedia(
            @RequestParam("username") String username,
            @RequestParam("media") String media
    ) {
        List<PostResponseDTO> results = new ArrayList<>();
        results.addAll(mapPostsToDTOs(
                postRepository.findByUser_Id_UsernameAndUser_Id_SocialMedia(username, media), analysisResultRepository));
        results.addAll(mapRepostsToDTOs(
                repostRepository.findByUserEntity_Id_UsernameAndUserEntity_Id_SocialMedia(username, media)));
        return results;
    }

    @GetMapping("/by-name")
    public List<PostResponseDTO> getPostsByName(@RequestParam("name") String name) {
        List<PostResponseDTO> results = new ArrayList<>();
        results.addAll(mapPostsToDTOs(
                postRepository.findByUser_FirstNameIgnoreCaseOrUser_LastNameIgnoreCase(name, name), analysisResultRepository));
        results.addAll(mapRepostsToDTOs(
                repostRepository.findByUserEntity_FirstNameIgnoreCaseOrUserEntity_LastNameIgnoreCase(name, name)));
        return results;
    }

    @PostMapping("/project-summary-by-posts")
    public AnalysisSummaryByPostResponseDTO getProjectSummaryByPosts(@RequestBody AnalysisSummaryByPostRequestDTO request) {
        List<AnalysisResult> results = request.getPosts().stream()
                .flatMap(postKey -> analysisResultRepository
                        .findByPostCompositeKey(postKey.getUsername(), postKey.getTime(), postKey.getSocialMedia())
                        .stream())
                .collect(Collectors.toList());

        Set<String> projectNames = results.stream()
                .map(ar -> ar.getProject().getProjectName())
                .collect(Collectors.toSet());

        Map<String, Set<String>> categoryToPostKeys = new HashMap<>();
        for (AnalysisResult result : results) {
            String category = result.getAnalysisCategory().getCategoryName();
            String key = result.getUsername() + "|" + result.getTime() + "|" + result.getSocialMedia();
            if (result.getAnalysisCategory().getCategoryResult() != null && !result.getAnalysisCategory().getCategoryResult().isEmpty()) {
                categoryToPostKeys.computeIfAbsent(category, k -> new HashSet<>()).add(key);
            }
        }

        int totalPosts = request.getPosts().size();
        List<AnalysisCategorySummaryDTO> categorySummaries = categoryToPostKeys.entrySet().stream()
                .map(e -> new AnalysisCategorySummaryDTO(e.getKey(), (100.0 * e.getValue().size()) / totalPosts))
                .toList();

        return new AnalysisSummaryByPostResponseDTO(new ArrayList<>(projectNames), categorySummaries);
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody CreatePostDTO request) {
        if (request.getNumberOfLikes() < 0 || request.getNumberOfDislikes() < 0) {
            return ResponseEntity.badRequest().body("Likes and dislikes must be non-negative.");
        }

        UserId userId = new UserId(request.getUsername(), request.getSocialMedia());
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("User does not exist.");
        }

        PostId postId = new PostId(request.getUsername(), request.getTime(), request.getSocialMedia());
        Post post = new Post();
        post.setUsername(postId.getUsername());
        post.setTime(postId.getTime());
        post.setSocialMedia(postId.getSocialMedia());
        post.setUser(user);
        post.setTime(request.getTime());
        post.setText(request.getText());
        post.setLocation(request.getLocation());
        post.setNumberOfLikes(request.getNumberOfLikes());
        post.setNumberOfDislikes(request.getNumberOfDislikes());
        post.setContainsMultimedia(request.getContainsMultimedia());

        postRepository.save(post);
        return ResponseEntity.ok("Post created successfully.");
    }
}