package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.smu.database.key.AnalysisResultId;

@Data
@Entity
@IdClass(AnalysisResultId.class)
@Table(name = "Analysis_Result")
public class AnalysisResult {

    @Id
    @Column(name = "Post_ID") // match DB
    private Integer postId;

    @Id
    @Column(name = "Project_Name") // match DB
    private String projectName;

    @Id
    @Column(name = "AnalysisCategory_ID") // match DB
    private Integer analysisCategoryId;

    @ManyToOne
    @JoinColumn(name = "Post_ID", insertable = false, updatable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "Project_Name", insertable = false, updatable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "AnalysisCategory_ID", insertable = false, updatable = false)
    private AnalysisCategory analysisCategory;
}
