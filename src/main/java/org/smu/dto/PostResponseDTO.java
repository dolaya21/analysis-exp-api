package org.smu.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponseDTO {
    private Integer postId; // null for reposts
    private String socialMedia;
    private String username;
    private String text;
    private String location;
    private Integer numberOfLikes;
    private Boolean containsMultimedia;
    private LocalDateTime time;
    private boolean isRepost;
    private String originalUsername;
    private LocalDateTime originalTime;
}
