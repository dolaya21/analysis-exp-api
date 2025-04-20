package org.smu.database.repository;

import org.smu.database.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<Post, Integer> {}
