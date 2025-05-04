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
    @Column(name = "Post_ID")
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "Social_Media")
    private SocialMedia socialMedia;

    @ManyToOne
    @JoinColumn(name = "Username")
    private User user;

    private LocalDateTime time;
    private String text;
    private String location;
    @Column(name = "Number_of_Likes")
    private Integer numberOfLikes;
    @Column(name = "Contains_Multimedia")
    private Boolean containsMultimedia;
}
