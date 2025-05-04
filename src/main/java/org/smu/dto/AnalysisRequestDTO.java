package org.smu.dto;

import lombok.Data;

@Data
public class AnalysisRequestDTO {
    private Integer postId;
    private String projectName;
    private String categoryName;
    private String categoryResult;
}
