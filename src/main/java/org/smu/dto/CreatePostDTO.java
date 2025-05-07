package org.smu.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatePostDTO {
    private Integer postId;
    private String username;
    private String socialMedia;
    private LocalDateTime time;
    private String text;
    private String location;
    private Integer numberOfLikes;
    private Integer numberOfDislikes;
    private Boolean containsMultimedia;
}
