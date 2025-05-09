package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.smu.database.key.PostId;

import java.time.LocalDateTime;

@Entity
@Data
@IdClass(PostId.class)
@Table(name = "Post")
public class Post {

    @Id
    @Column(name = "Username")
    private String username;

    @Id
    @Column(name = "Time")
    private LocalDateTime time;

    @Id
    @Column(name = "Social_Media")
    private String socialMedia;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Username", referencedColumnName = "Username", insertable = false, updatable = false),
            @JoinColumn(name = "Social_Media", referencedColumnName = "Social_Media", insertable = false, updatable = false)
    })
    private User user;

    @ManyToOne
    @JoinColumn(name = "Social_Media", referencedColumnName = "Name", insertable = false, updatable = false)
    private SocialMedia socialMediaEntity;

    private String text;
    private String location;

    @Column(name = "Number_of_Likes")
    private Integer numberOfLikes;

    @Column(name = "Number_of_Dislikes")
    private Integer numberOfDislikes;

    @Column(name = "Contains_Multimedia")
    private Boolean containsMultimedia;
}
