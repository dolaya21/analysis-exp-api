package org.smu.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostWithResultsDTO {
    private String username;
    private String socialMedia;
    private LocalDateTime time;
    private String text;
    private String location;
    private List<AnalysisResultDTO> analysisResults;
}
