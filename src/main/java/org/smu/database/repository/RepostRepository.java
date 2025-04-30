package org.smu.database.repository;

import org.smu.database.entity.Repost;
import org.smu.database.key.RepostId;
import org.springframework.data.jpa.repository.JpaRepository;

interface RepostRepository extends JpaRepository<Repost, RepostId> {}

