package org.smu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnalysisSummaryByPostResponseDTO {
    private List<String> projectNames;
    private List<AnalysisCategorySummaryDTO> categorySummaries;
}
