package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Project {
    @Id
    @Column(name = "Project_Name")
    private String projectName;
    @ManyToOne
    @JoinColumn(name = "project_manager_employee_id")
    private ProjectManager projectManager;
    @Column(name = "Institute_name")
    private String instituteName;
    @Column(name ="start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
}
