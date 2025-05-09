package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Analysis_Category")
public class AnalysisCategory {

    @Id
    @Column(name = "Category_Name")
    private String categoryName;

    @Column(name = "Category_Result")
    private String categoryResult;
}
