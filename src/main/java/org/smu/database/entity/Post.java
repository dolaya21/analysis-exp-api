package org.smu.database.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Post {
    @Id
    @Column(name = "Post_ID")
    private Integer postId;

    @ManyToOne
    @JoinColumn(name = "Social_Media", referencedColumnName = "Name", insertable = false, updatable = false)
    private SocialMedia socialMedia;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "Username", referencedColumnName = "Username"),
            @JoinColumn(name = "Social_Media", referencedColumnName = "Social_Media")
    })
    private User user;

    private LocalDateTime time;
    private String text;
    private String location;

    @Column(name = "Number_of_Likes")
    private Integer numberOfLikes;

    @Column(name = "Number_of_Dislikes")
    private Integer numberOfDislikes;

    @Column(name = "Contains_Multimedia")
    private Boolean containsMultimedia;
}
