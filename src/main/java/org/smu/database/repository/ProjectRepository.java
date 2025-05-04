package org.smu.database.repository;

import org.smu.database.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, String> {
    List<Project> findByProjectManager_EmployeeId(Integer employeeId);
}

