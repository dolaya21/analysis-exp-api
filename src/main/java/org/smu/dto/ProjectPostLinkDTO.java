package org.smu.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProjectPostLinkDTO {
    private String projectName;
    private List<PostDTO> posts;
}
