package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Analysis_Category")
public class AnalysisCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnalysisCategory_ID")
    private Integer analysisCategoryId;

    @Column(name = "Category_Name")
    private String categoryName;

    @Column(name = "Category_Result")
    private String categoryResult;
}
