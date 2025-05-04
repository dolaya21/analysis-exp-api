package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Project_Manager")
public class ProjectManager {
    @Id
    @Column(name = "Employee_Id")
    private Integer employeeId;
    @Column(name = "First_Name")
    private String firstName;
    @Column(name = "Last_Name")
    private String lastName;
}
