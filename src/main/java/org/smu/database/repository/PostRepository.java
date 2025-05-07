package org.smu.database.repository;

import org.smu.database.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findBySocialMedia_Name(String socialMediaName);

    List<Post> findByTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Post> findByUser_Id_UsernameAndUser_Id_SocialMedia(String username, String socialMedia);

    List<Post> findByUser_FirstNameIgnoreCaseOrUser_LastNameIgnoreCase(String firstName, String lastName);

    Optional<Post> findByPostId(Integer postId);
}
