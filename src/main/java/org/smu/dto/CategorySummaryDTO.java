package org.smu.dto;

import lombok.Data;

@Data
public class CategorySummaryDTO {
    private String categoryName;
    private double percentageOfPostsWithResult;
}