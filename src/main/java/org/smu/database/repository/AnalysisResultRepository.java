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

    @Query("""
    SELECT ar FROM AnalysisResult ar
    JOIN FETCH ar.analysisCategory ac
    WHERE ar.post.postId = :postId
""")
    List<AnalysisResult> findByPostId(Integer postId);

    @Query("""
    SELECT ar FROM AnalysisResult ar
    JOIN FETCH ar.analysisCategory ac
    JOIN FETCH ar.project p
    WHERE ar.post.postId IN :postIds
""")
    List<AnalysisResult> findByPostIdIn(List<Integer> postIds);
}
