package org.smu.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnalysisResultDTO {
    private String username;
    private LocalDateTime time;
    private String socialMedia;
    private String projectName;
    private String categoryName;
    private String categoryResult;
}
