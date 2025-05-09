package org.smu.util;

import org.smu.database.entity.Post;
import org.smu.database.entity.Repost;
import org.smu.database.key.PostId;
import org.smu.database.repository.AnalysisResultRepository;
import org.smu.dto.PostResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class PostUtils {

    public static List<PostResponseDTO> mapPostsToDTOs(List<Post> posts, AnalysisResultRepository analysisRepo) {
        List<PostResponseDTO> dtos = new ArrayList<>();
        for (Post p : posts) {
            PostResponseDTO dto = new PostResponseDTO();
            PostId id = new PostId(p.getUsername(), p.getTime(), p.getSocialMedia());

            dto.setUsername(id.getUsername());
            dto.setTime(id.getTime());
            dto.setSocialMedia(id.getSocialMedia());

            dto.setText(p.getText());
            dto.setLocation(p.getLocation());
            dto.setNumberOfLikes(p.getNumberOfLikes());
            dto.setNumberOfDislikes(p.getNumberOfDislikes());
            dto.setContainsMultimedia(p.getContainsMultimedia());
            dto.setRepost(false);

            List<String> projectNames = analysisRepo
                    .findByPostCompositeKey(id.getUsername(), id.getTime(), id.getSocialMedia())
                    .stream()
                    .map(r -> r.getProject().getProjectName())
                    .distinct()
                    .toList();

            dto.setProjectNames(projectNames);
            dtos.add(dto);
        }
        return dtos;
    }

    public static List<PostResponseDTO> mapRepostsToDTOs(List<Repost> reposts) {
        List<PostResponseDTO> dtos = new ArrayList<>();
        for (Repost r : reposts) {
            PostResponseDTO dto = new PostResponseDTO();
            dto.setUsername(r.getUserEntity().getId().getUsername());
            dto.setSocialMedia(r.getId().getSocialMedia());
            dto.setTime(r.getId().getTime());
            dto.setRepost(true);
            dto.setOriginalUsername(r.getRepostUserEntity().getId().getUsername());
            dto.setOriginalTime(r.getRepostTime());
            dto.setProjectNames(new ArrayList<>()); // reposts don't have associated analysis
            dtos.add(dto);
        }
        return dtos;
    }
}