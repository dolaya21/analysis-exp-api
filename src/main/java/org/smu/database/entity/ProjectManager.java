package org.smu.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class ProjectManager {
    @Id
    private Integer employeeId;
    private String firstName;
    private String lastName;
}
