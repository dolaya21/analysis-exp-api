package org.smu.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectRequestDTO {
    private String projectName;
    private Integer projectManagerEmployeeId;
    private String instituteName;
    private LocalDate startDate;
    private LocalDate endDate;
}
