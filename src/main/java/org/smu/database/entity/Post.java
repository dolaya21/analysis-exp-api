package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
@Table
public class Post {
    @Id
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "social_media")
    private SocialMedia socialMedia;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    private LocalDateTime time;
    private String text;
    private String location;
    private Integer numberOfLikes;
    private Boolean containsMultimedia;
}
