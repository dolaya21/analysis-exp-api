package org.smu.database.repository;

import org.smu.database.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

interface ProjectRepository extends JpaRepository<Project, String> {}

