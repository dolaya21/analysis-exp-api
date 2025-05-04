package org.smu.database.repository;

import org.smu.database.entity.AnalysisResult;
import org.smu.database.key.AnalysisResultId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, AnalysisResultId> {}
