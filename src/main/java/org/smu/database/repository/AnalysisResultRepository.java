package org.smu.database.repository;

import org.smu.database.entity.AnalysisResult;
import org.smu.database.key.AnalysisResultId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, AnalysisResultId> {
    @Query("""
        SELECT ar FROM AnalysisResult ar
        JOIN FETCH ar.analysisCategory ac
        WHERE ar.project.projectName = :projectName
    """)
    List<AnalysisResult> findByProjectName(@Param("projectName") String projectName);

    @Query("""
    SELECT ar FROM AnalysisResult ar
    JOIN FETCH ar.analysisCategory ac
    JOIN FETCH ar.project p
    WHERE ar.username = :username AND ar.time = :time AND ar.socialMedia = :socialMedia
""")
    List<AnalysisResult> findByPostCompositeKey(
            @Param("username") String username,
            @Param("time") LocalDateTime time,
            @Param("socialMedia") String socialMedia
    );

    @Query("""
        SELECT ar FROM AnalysisResult ar
        JOIN FETCH ar.analysisCategory ac
        JOIN FETCH ar.project p
        WHERE (ar.username, ar.time, ar.socialMedia) IN :postKeys
    """)
    List<AnalysisResult> findByPostKeysIn(
            @Param("postKeys") List<Object[]> postKeys
    );
}
