package org.smu.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private String username;
    private String socialMedia;
    private LocalDateTime time;
    private String text;
    private String location;
    private Integer numberOfLikes;
    private Integer numberOfDislikes;
    private Boolean containsMultimedia;
    private boolean isRepost;
    private String originalUsername;
    private LocalDateTime originalTime;
    private List<AnalysisResultDTO> analysisResults;
}
