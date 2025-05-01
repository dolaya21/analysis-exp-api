package org.smu.controller;

import org.smu.database.entity.*;
import org.smu.database.repository.*;
import org.smu.dto.PostDTO;
import org.smu.dto.ProjectPostLinkDTO;
import org.smu.dto.ProjectRequestDTO;
import org.smu.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                Optional<User> userOpt = userRepository.findById(postDto.getUsername());
                Optional<SocialMedia> smOpt = socialMediaRepository.findById(postDto.getSocialMedia());

                if (userOpt.isEmpty() || smOpt.isEmpty()) {
                    return ResponseEntity.badRequest().body("User or Social Media not found for post ID: " + postDto.getPostId());
                }

                post = new Post();
                post.setPostId(postDto.getPostId());
                post.setUser(userOpt.get());
                post.setSocialMedia(smOpt.get());
                post.setTime(postDto.getTime());
                post.setText(postDto.getText());
                post.setLocation(postDto.getLocation());
                post.setNumberOfLikes(postDto.getNumberOfLikes());
                post.setContainsMultimedia(postDto.getContainsMultimedia());

                postRepository.save(post);
            }

            savedPosts.add(post);
        }

        return ResponseEntity.ok("Posts linked to project successfully.");
    }
}