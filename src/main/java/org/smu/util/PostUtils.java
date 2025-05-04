package org.smu.util;

import org.smu.database.entity.Post;
import org.smu.database.entity.Repost;
import org.smu.database.repository.AnalysisResultRepository;
import org.smu.dto.PostResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class PostUtils {

    public static List<PostResponseDTO> mapPostsToDTOs(List<Post> posts, AnalysisResultRepository analysisRepo) {
        List<PostResponseDTO> dtos = new ArrayList<>();
        for (Post p : posts) {
            PostResponseDTO dto = new PostResponseDTO();
            dto.setPostId(p.getPostId());
            dto.setSocialMedia(p.getSocialMedia().getName());
            dto.setUsername(p.getUser().getUsername());
            dto.setText(p.getText());
            dto.setLocation(p.getLocation());
            dto.setNumberOfLikes(p.getNumberOfLikes());
            dto.setContainsMultimedia(p.getContainsMultimedia());
            dto.setTime(p.getTime());
            dto.setRepost(false);

            List<String> projectNames = analysisRepo.findByPostId(p.getPostId()).stream()
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
            dto.setPostId(null);
            dto.setSocialMedia(r.getSocialMediaEntity().getName());
            dto.setUsername(r.getUserEntity().getUsername());
            dto.setTime(r.getId().getTime());
            dto.setRepost(true);
            dto.setOriginalUsername(r.getRepostUserEntity().getUsername());
            dto.setOriginalTime(r.getRepostTime());
            dto.setProjectNames(new ArrayList<>());

            dtos.add(dto);
        }
        return dtos;
    }
}
