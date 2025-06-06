package org.smu.controller;

import org.smu.database.entity.AnalysisCategory;
import org.smu.database.entity.AnalysisResult;
import org.smu.database.entity.Post;
import org.smu.database.entity.Project;
import org.smu.database.key.PostId;
import org.smu.database.repository.AnalysisCategoryRepository;
import org.smu.database.repository.AnalysisResultRepository;
import org.smu.database.repository.PostRepository;
import org.smu.database.repository.ProjectRepository;
import org.smu.dto.AnalysisResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private PostRepository postRepository;
    @Autowired private ProjectRepository projectRepository;
    @Autowired private AnalysisCategoryRepository categoryRepository;
    @Autowired private AnalysisResultRepository resultRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addAnalysis(@RequestBody AnalysisResultDTO request) {
        PostId postId = new PostId(request.getUsername(), request.getTime(), request.getSocialMedia());
        Optional<Post> postOpt = postRepository.findById(postId);
        Optional<Project> projectOpt = projectRepository.findById(request.getProjectName());

        if (postOpt.isEmpty() || projectOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Post or Project not found.");
        }

        AnalysisCategory category = new AnalysisCategory();
        category.setCategoryName(request.getCategoryName());
        category.setCategoryResult(request.getCategoryResult());
        category = categoryRepository.save(category);

        AnalysisResult result = new AnalysisResult();
        result.setUsername(request.getUsername());
        result.setTime(request.getTime());
        result.setSocialMedia(request.getSocialMedia());
        result.setProjectName(request.getProjectName());
        result.setCategoryName(category.getCategoryName());

        result.setPost(postOpt.get());
        result.setProject(projectOpt.get());
        result.setAnalysisCategory(category);

        resultRepository.save(result);

        return ResponseEntity.ok("Analysis recorded successfully.");
    }
}
