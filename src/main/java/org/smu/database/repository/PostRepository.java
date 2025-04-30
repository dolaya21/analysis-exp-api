package org.smu.database.repository;

import org.smu.database.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findBySocialMedia_Name(String socialMediaName);

    List<Post> findByTimeBetween(LocalDateTime start, LocalDateTime end);
}
