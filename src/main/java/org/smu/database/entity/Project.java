package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
@Entity
public class Project {
    @Id
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "project_manager_employee_id")
    private ProjectManager projectManager;

    private String instituteName;
    private LocalDate startDate;
    private LocalDate endDate;
}
