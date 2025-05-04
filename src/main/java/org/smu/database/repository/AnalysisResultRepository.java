package org.smu.database.repository;

import org.smu.database.entity.AnalysisResult;
import org.smu.database.key.AnalysisResultId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, AnalysisResultId> {
    @Query("""
        SELECT ar FROM AnalysisResult ar
        JOIN FETCH ar.analysisCategory ac
        WHERE ar.project.projectName = :projectName
    """)
    List<AnalysisResult> findByProjectName(String projectName);
}
