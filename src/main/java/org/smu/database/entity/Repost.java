package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.smu.database.key.RepostId;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Repost")
public class Repost {

    @EmbeddedId
    private RepostId id;

    @ManyToOne
    @MapsId("socialMedia")
    @JoinColumn(name = "social_media")
    private SocialMedia socialMediaEntity;

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "user")
    private User userEntity;

    @ManyToOne
    @JoinColumn(name = "repost_username")
    private User repostUserEntity;

    @Column(name = "repost_time")
    private LocalDateTime repostTime;
}
