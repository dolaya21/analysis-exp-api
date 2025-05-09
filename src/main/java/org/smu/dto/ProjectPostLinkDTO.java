package org.smu.dto;

import lombok.Data;
import org.smu.database.key.PostId;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectPostLinkDTO {
    private String projectName;
    private List<PostId> postIds;
    private String categoryName;
    private String categoryResult;
}
