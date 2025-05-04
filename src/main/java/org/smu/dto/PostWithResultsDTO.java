package org.smu.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostWithResultsDTO {
    private Integer postId;
    private String username;
    private String text;
    private String location;
    private String socialMedia;
    private LocalDateTime time;
    private List<AnalysisResultDTO> analysisResults;
}
