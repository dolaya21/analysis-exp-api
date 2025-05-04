package org.smu.controller;

import org.smu.database.entity.Project;
import org.smu.database.entity.ProjectManager;
import org.smu.database.repository.ProjectManagerRepository;
import org.smu.database.repository.ProjectRepository;
import org.smu.dto.ProjectManagerRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project-managers")
public class ProjectManagerController {
    @Autowired
    private ProjectManagerRepository projectManagerRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping
    public ResponseEntity<?> createProjectManager(@RequestBody ProjectManagerRequestDTO request) {
        if (projectManagerRepository.existsById(request.getEmployeeId())) {
            return ResponseEntity.badRequest().body("Project manager with this ID already exists.");
        }

        ProjectManager pm = new ProjectManager();
        pm.setEmployeeId(request.getEmployeeId());
        pm.setFirstName(request.getFirstName());
        pm.setLastName(request.getLastName());

        projectManagerRepository.save(pm);
        return ResponseEntity.ok("Project manager created successfully.");
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getProjectsByManagerId(@RequestParam Integer managerId) {
        if (!projectManagerRepository.existsById(managerId)) {
            return ResponseEntity.badRequest().body("Project manager not found.");
        }

        List<Project> projects = projectRepository.findByProjectManager_EmployeeId(managerId);
        List<String> projectNames = projects.stream()
                .map(Project::getProjectName)
                .collect(Collectors.toList());

        return ResponseEntity.ok(projectNames);
    }
}
