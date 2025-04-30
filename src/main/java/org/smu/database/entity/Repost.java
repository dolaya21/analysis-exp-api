package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.smu.database.key.RepostId;

import java.time.LocalDateTime;

@Data
@Entity
@IdClass(RepostId.class)
public class Repost {
    @Id
    @ManyToOne
    @JoinColumn(name = "social_media")
    private SocialMedia socialMedia;

    @Id
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @Id
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "repost_username")
    private User repostUsername;

    private LocalDateTime repostTime;
}
