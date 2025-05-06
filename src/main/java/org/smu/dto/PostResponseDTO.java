package org.smu.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostResponseDTO {
    private Integer postId;
    private String socialMedia;
    private String username;
    private String text;
    private String location;
    private Integer numberOfLikes;
    private Integer numberOfDislikes;
    private Boolean containsMultimedia;
    private LocalDateTime time;
    private boolean isRepost;
    private String originalUsername;
    private LocalDateTime originalTime;
    private List<String> projectNames;
}
