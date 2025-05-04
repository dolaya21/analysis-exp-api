package org.smu.controller;

import org.smu.database.entity.AnalysisResult;
import org.smu.database.repository.PostRepository;
import org.smu.database.repository.RepostRepository;
import org.smu.database.repository.AnalysisResultRepository;
import org.smu.dto.AnalysisCategorySummaryDTO;
import org.smu.dto.AnalysisSummaryByPostRequestDTO;
import org.smu.dto.AnalysisSummaryByPostResponseDTO;
import org.smu.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    private AnalysisResultRepository analysisResultRepository;

    @GetMapping("/by-social-media/{name}")
    public List<PostResponseDTO> getPostsBySocialMedia(@PathVariable String name) {
        List<PostResponseDTO> results = new ArrayList<>();
        results.addAll(mapPostsToDTOs(postRepository.findBySocialMedia_Name(name), analysisResultRepository));
        results.addAll(mapRepostsToDTOs(repostRepository.findBySocialMediaEntity_Name(name)));
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
                postRepository.findByUser_UsernameAndSocialMedia_Name(username, media), analysisResultRepository));
        results.addAll(mapRepostsToDTOs(
                repostRepository.findByUserEntity_UsernameAndSocialMediaEntity_Name(username, media)));
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
        List<AnalysisResult> results = analysisResultRepository.findByPostIdIn(request.getPostIds());

        Set<String> projectNames = results.stream()
                .map(ar -> ar.getProject().getProjectName())
                .collect(Collectors.toSet());

        Map<String, Set<Integer>> categoryToPostIdsWithResult = new HashMap<>();
        for (AnalysisResult result : results) {
            String category = result.getAnalysisCategory().getCategoryName();
            if (result.getAnalysisCategory().getCategoryResult() != null && !result.getAnalysisCategory().getCategoryResult().isEmpty()) {
                categoryToPostIdsWithResult
                        .computeIfAbsent(category, k -> new HashSet<>())
                        .add(result.getPost().getPostId());
            }
        }

        List<AnalysisCategorySummaryDTO> categorySummaries = new ArrayList<>();
        for (String category : categoryToPostIdsWithResult.keySet()) {
            double percentage = 100.0 * categoryToPostIdsWithResult.get(category).size() / request.getPostIds().size();
            categorySummaries.add(new AnalysisCategorySummaryDTO(category, percentage));
        }

        return new AnalysisSummaryByPostResponseDTO(new ArrayList<>(projectNames), categorySummaries);
    }

}
