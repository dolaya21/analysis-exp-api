package org.smu.dto;

import lombok.Data;

@Data
public class AnalysisResultDTO {
    private Integer postId;
    private String projectName;
    private String categoryName;
    private String categoryResult;
}
