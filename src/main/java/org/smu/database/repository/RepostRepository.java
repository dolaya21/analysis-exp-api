package org.smu.database.repository;

import org.smu.database.entity.Repost;
import org.smu.database.key.RepostId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RepostRepository extends JpaRepository<Repost, RepostId> {
    List<Repost> findById_SocialMedia(String name);

    List<Repost> findById_TimeBetween(LocalDateTime start, LocalDateTime end);

    List<Repost> findByUserEntity_Id_UsernameAndUserEntity_Id_SocialMedia(String username, String socialMedia);

    List<Repost> findByUserEntity_FirstNameIgnoreCaseOrUserEntity_LastNameIgnoreCase(String firstName, String lastName);
}

