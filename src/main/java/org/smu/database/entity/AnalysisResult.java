package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.smu.database.key.AnalysisResultId;

import java.time.LocalDateTime;

@Data
@Entity
@IdClass(AnalysisResultId.class)
@Table(name = "Analysis_Result")
public class AnalysisResult {

    @Id
    @Column(name = "Username")
    private String username;

    @Id
    @Column(name = "Time")
    private LocalDateTime time;

    @Id
    @Column(name = "Social_Media")
    private String socialMedia;

    @Id
    @Column(name = "Project_Name")
    private String projectName;

    @Id
    @Column(name = "Category_Name")
    private String categoryName;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Username", referencedColumnName = "Username", insertable = false, updatable = false),
            @JoinColumn(name = "Time", referencedColumnName = "Time", insertable = false, updatable = false),
            @JoinColumn(name = "Social_Media", referencedColumnName = "Social_Media", insertable = false, updatable = false)
    })
    private Post post;

    @ManyToOne
    @JoinColumn(name = "Project_Name", referencedColumnName = "Project_Name", insertable = false, updatable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "Category_Name", referencedColumnName = "Category_Name", insertable = false, updatable = false)
    private AnalysisCategory analysisCategory;
}