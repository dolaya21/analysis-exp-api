package org.smu.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectAnalysisSummaryDTO {
    private List<PostWithResultsDTO> posts;
    private List<AnalysisCategorySummaryDTO> categorySummaries;
}
