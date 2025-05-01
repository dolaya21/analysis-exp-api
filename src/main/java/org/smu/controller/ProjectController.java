package org.smu.controller;

import org.smu.database.entity.Project;
import org.smu.database.entity.ProjectManager;
import org.smu.database.repository.ProjectManagerRepository;
import org.smu.database.repository.ProjectRepository;
import org.smu.dto.ProjectRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectManagerRepository projectManagerRepository;

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
}