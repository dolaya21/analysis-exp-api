package org.smu.controller;

import org.smu.database.entity.*;
import org.smu.database.key.PostId;
import org.smu.database.key.UserId;
import org.smu.database.repository.*;
import org.smu.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
    @Autowired
    private AnalysisCategoryRepository analysisCategoryRepository;


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

        List<PostId> postIds = request.getPostIds();
        if (postIds == null || postIds.isEmpty()) {
            return ResponseEntity.badRequest().body("No post IDs provided.");
        }

        AnalysisCategory category = analysisCategoryRepository.findById(request.getCategoryName())
                .orElseGet(() -> {
                    AnalysisCategory newCategory = new AnalysisCategory();
                    newCategory.setCategoryName(request.getCategoryName());
                    newCategory.setCategoryResult(request.getCategoryResult());
                    return analysisCategoryRepository.save(newCategory);
                });

        for (PostId postId : postIds) {
            Optional<Post> postOpt = postRepository.findById(postId);
            if (postOpt.isEmpty()) continue;
            Post post = postOpt.get();

            AnalysisResult result = new AnalysisResult();
            result.setUsername(post.getUsername());
            result.setTime(post.getTime());
            result.setSocialMedia(post.getSocialMedia());
            result.setProjectName(projectOpt.get().getProjectName());
            result.setCategoryName(category.getCategoryName());
            result.setPost(post);
            result.setProject(projectOpt.get());
            category.setCategoryResult(request.getCategoryResult());
            result.setAnalysisCategory(category);
            analysisResultRepository.save(result);
        }

        return ResponseEntity.ok("Analysis results linked to project successfully for provided posts.");
    }

    @GetMapping("/analysis-results")
    public ResponseEntity<?> getAnalysisResultsByProjectName(@RequestParam String projectName) {
        List<AnalysisResult> results = analysisResultRepository.findByProjectName(projectName);

        List<AnalysisResultDTO> dtos = results.stream().map(ar -> {
            AnalysisResultDTO dto = new AnalysisResultDTO();
            dto.setUsername(ar.getUsername());
            dto.setTime(ar.getTime());
            dto.setSocialMedia(ar.getSocialMedia());
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

        Map<String, List<AnalysisResult>> groupedByPost = results.stream()
                .collect(Collectors.groupingBy(r -> r.getUsername() + "|" + r.getTime() + "|" + r.getSocialMedia()));

        List<PostWithResultsDTO> postDTOs = new ArrayList<>();
        for (Map.Entry<String, List<AnalysisResult>> entry : groupedByPost.entrySet()) {
            Post post = entry.getValue().get(0).getPost();
            PostId id = new PostId(post.getUsername(), post.getTime(), post.getSocialMedia());

            PostWithResultsDTO dto = new PostWithResultsDTO();
            dto.setUsername(id.getUsername());
            dto.setTime(id.getTime());
            dto.setSocialMedia(id.getSocialMedia());
            dto.setText(post.getText());
            dto.setLocation(post.getLocation());

            List<AnalysisResultDTO> arDTOs = entry.getValue().stream().map(ar -> {
                AnalysisResultDTO ardto = new AnalysisResultDTO();
                ardto.setCategoryName(ar.getAnalysisCategory().getCategoryName());
                ardto.setCategoryResult(ar.getAnalysisCategory().getCategoryResult());
                ardto.setProjectName(projectName);
                ardto.setUsername(ar.getUsername());
                ardto.setTime(ar.getTime());
                ardto.setSocialMedia(ar.getSocialMedia());
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
                        Collectors.mapping(ar -> ar.getUsername() + "|" + ar.getTime() + "|" + ar.getSocialMedia(), Collectors.toSet())
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (long) e.getValue().size()));

        List<AnalysisCategorySummaryDTO> categorySummaries = countByCategory.entrySet().stream().map(e -> {
            AnalysisCategorySummaryDTO cs = new AnalysisCategorySummaryDTO();
            cs.setCategoryName(e.getKey());
            cs.setPercentageOfPostsWithResult((e.getValue() * 100.0) / totalPosts);
            return cs;
        }).toList();

        ProjectAnalysisSummaryDTO response = new ProjectAnalysisSummaryDTO();
        response.setPosts(postDTOs);
        response.setCategorySummaries(categorySummaries);

        return ResponseEntity.ok(response);
    }
}