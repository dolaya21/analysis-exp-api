package org.smu.database.repository;

import org.smu.database.entity.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;

interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Integer> {}
