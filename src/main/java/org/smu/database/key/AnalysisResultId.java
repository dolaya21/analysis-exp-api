package org.smu.database.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisResultId implements Serializable {
    private String username;
    private LocalDateTime time;
    private String socialMedia;
    private String projectName;
    private String categoryName;
}
