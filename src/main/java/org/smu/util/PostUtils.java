package org.smu.util;

import org.smu.database.entity.Post;
import org.smu.database.entity.Repost;
import org.smu.database.entity.AnalysisResult;
import org.smu.database.repository.AnalysisResultRepository;
import org.smu.dto.PostResponseDTO;
import org.smu.dto.AnalysisResultDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

            // ✅ Fetch analysis results using postId
            List<AnalysisResult> results = analysisRepo.findByPostId(p.getPostId());
            List<AnalysisResultDTO> resultDTOs = results.stream().map(r -> {
                AnalysisResultDTO dtoR = new AnalysisResultDTO();
                dtoR.setProjectName(r.getProject().getProjectName());
                dtoR.setCategoryName(r.getAnalysisCategory().getCategoryName());
                dtoR.setCategoryResult(r.getAnalysisCategory().getCategoryResult());
                return dtoR;
            }).collect(Collectors.toList());

            dto.setAnalysisResults(resultDTOs);
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
            dto.setAnalysisResults(new ArrayList<>()); // or fetch analysis if needed
            dtos.add(dto);
        }
        return dtos;
    }
}
