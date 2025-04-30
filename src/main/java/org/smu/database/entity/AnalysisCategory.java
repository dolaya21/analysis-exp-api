package org.smu.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AnalysisCategory {
    @Id
    private Integer analysisCategoryId;
    private String categoryName;
    private String categoryResult;
}
