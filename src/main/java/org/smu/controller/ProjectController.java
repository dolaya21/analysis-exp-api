package org.smu.controller;

import org.smu.database.entity.*;
import org.smu.database.key.UserId;
import org.smu.database.repository.*;
import org.smu.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private AnalysisResultRepository analysisResultRepository;

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDTO request) {
        Optional<ProjectManager> pmOpt = projectManagerRepository.findById(request.getProjectManagerEmployeeId());
        if (pmOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Project manager not found");
        }

        Project project = new Project();
        project.setProjectName(request.getProjectName());
        project.setInstituteName(request.getInstituteName());
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setProjectManager(pmOpt.get());

        projectRepository.save(project);
        return ResponseEntity.ok("Project created successfully");
    }

    @PostMapping("/add-posts")
    public ResponseEntity<?> linkPostsToProject(@RequestBody ProjectPostLinkDTO request) {
        Optional<Project> projectOpt = projectRepository.findById(request.getProjectName());
        if (projectOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Project not found");
        }

        List<Post> savedPosts = new ArrayList<>();

        for (PostDTO postDto : request.getPosts()) {
            Optional<Post> existingPostOpt = postRepository.findByPostId(postDto.getPostId());
            Post post;

            if (existingPostOpt.isPresent()) {
                post = existingPostOpt.get();
            } else {
                Optional<User> userOpt = userRepository.findById(new UserId(postDto.getUsername(), postDto.getSocialMedia()));
                Optional<SocialMedia> smOpt = socialMediaRepository.findById(postDto.getSocialMedia());

                if (userOpt.isEmpty() || smOpt.isEmpty()) {
                    return ResponseEntity.badRequest().body("User or Social Media not found for post ID: " + postDto.getPostId());
                }

                post = new Post();
                post.setPostId(postDto.getPostId());
                post.setUser(userOpt.get());
                post.setSocialMedia(smOpt.get());
                post.setTime(postDto.getOriginalTime());
                post.setText(postDto.getText());
                post.setLocation(postDto.getLocation());
                post.setNumberOfLikes(postDto.getNumberOfLikes());
                post.setNumberOfDislikes(postDto.getNumberOfDislikes());
                post.setContainsMultimedia(postDto.getContainsMultimedia());

                postRepository.save(post);
            }

            savedPosts.add(post);
        }

        return ResponseEntity.ok("Posts linked to project successfully.");
    }

    @GetMapping("/analysis-results")
    public ResponseEntity<?> getAnalysisResultsByProjectName(@RequestParam String projectName) {
        List<AnalysisResult> results = analysisResultRepository.findByProjectName(projectName);

        List<AnalysisResultDTO> dtos = results.stream().map(ar -> {
            AnalysisResultDTO dto = new AnalysisResultDTO();
            dto.setPostId(ar.getPost().getPostId());
            dto.setProjectName(ar.getProject().getProjectName());
            dto.setCategoryName(ar.getAnalysisCategory().getCategoryName());
            dto.setCategoryResult(ar.getAnalysisCategory().getCategoryResult());
            return dto;
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{projectName}/analysis-summary")
    public ResponseEntity<?> getProjectAnalysisSummary(@PathVariable String projectName) {
        Optional<Project> projectOpt = projectRepository.findById(projectName);
        if (projectOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Project not found");
        }

        Project project = projectOpt.get();
        List<AnalysisResult> results = analysisResultRepository.findByProjectName(projectName);

        Map<Integer, List<AnalysisResult>> groupedByPost = results.stream()
                .collect(Collectors.groupingBy(r -> r.getPost().getPostId()));

        List<PostWithResultsDTO> postDTOs = new ArrayList<>();
        for (Map.Entry<Integer, List<AnalysisResult>> entry : groupedByPost.entrySet()) {
            Post post = entry.getValue().get(0).getPost();

            PostWithResultsDTO dto = new PostWithResultsDTO();
            dto.setPostId(post.getPostId());
            dto.setUsername(post.getUser().getId().getUsername());
            dto.setText(post.getText());
            dto.setLocation(post.getLocation());
            dto.setSocialMedia(post.getSocialMedia().getName());
            dto.setTime(post.getTime());

            List<AnalysisResultDTO> arDTOs = entry.getValue().stream().map(ar -> {
                AnalysisResultDTO ardto = new AnalysisResultDTO();
                ardto.setCategoryName(ar.getAnalysisCategory().getCategoryName());
                ardto.setCategoryResult(ar.getAnalysisCategory().getCategoryResult());
                ardto.setProjectName(projectName);
                return ardto;
            }).toList();

            dto.setAnalysisResults(arDTOs);
            postDTOs.add(dto);
        }

        int totalPosts = groupedByPost.size();
        Map<String, Long> countByCategory = results.stream()
                .filter(ar -> ar.getAnalysisCategory().getCategoryResult() != null)
                .collect(Collectors.groupingBy(
                        ar -> ar.getAnalysisCategory().getCategoryName(),
                        Collectors.mapping(ar -> ar.getPost().getPostId(), Collectors.toSet())
                )).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (long) e.getValue().size()));

        List<AnalysisCategorySummaryDTO> categorySummaries = countByCategory.entrySet().stream().map(e -> {
            AnalysisCategorySummaryDTO cs = new AnalysisCategorySummaryDTO();
            cs.setCategoryName(e.getKey());
            cs.setPercentageOfPostsWithResult((e.getValue() * 100.0) / totalPosts);
            return cs;
        }).toList();

        // Final wrapper
        ProjectAnalysisSummaryDTO response = new ProjectAnalysisSummaryDTO();
        response.setPosts(postDTOs);
        response.setCategorySummaries(categorySummaries);

        return ResponseEntity.ok(response);
    }
}