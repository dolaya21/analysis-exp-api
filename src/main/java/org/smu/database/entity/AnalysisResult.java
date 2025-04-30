package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.smu.database.key.AnalysisResultId;

import java.sql.Date;

@Data
@Entity
@IdClass(AnalysisResultId.class)
public class AnalysisResult {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_name")
    private Project project;

    @Id
    @ManyToOne
    @JoinColumn(name = "analysis_category_id")
    private AnalysisCategory analysisCategory;
}
