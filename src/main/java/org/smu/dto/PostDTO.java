package org.smu.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Integer postId;
    private String socialMedia;
    private String username;
    private String text;
    private String location;
    private LocalDateTime time;
    private Integer numberOfLikes;
    private Boolean containsMultimedia;
}
