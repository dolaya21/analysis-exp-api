package org.smu.dto;

import lombok.Data;
import java.util.List;

@Data
public class AnalysisSummaryByPostRequestDTO {
    private List<CompositePostKeyDTO> posts;
}
