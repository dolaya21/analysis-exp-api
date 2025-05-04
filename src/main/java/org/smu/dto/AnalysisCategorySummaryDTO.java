package org.smu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisCategorySummaryDTO {
    private String categoryName;
    private double percentageOfPostsWithResult;
}