package org.smu.controller;

import org.smu.database.entity.ProjectManager;
import org.smu.database.repository.ProjectManagerRepository;
import org.smu.dto.ProjectManagerRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/project-managers")
public class ProjectManagerController {
    @Autowired
    private ProjectManagerRepository projectManagerRepository;

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
}
