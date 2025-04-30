package org.smu.controller;

import org.smu.database.entity.Post;
import org.smu.database.entity.Repost;
import org.smu.database.repository.PostRepository;
import org.smu.database.repository.RepostRepository;
import org.smu.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private RepostRepository repostRepository;

    @GetMapping("/by-social-media/{name}")
    public List<PostResponseDTO> getPostsBySocialMedia(@PathVariable String name) {
        List<PostResponseDTO> results = new ArrayList<>();

        // Fetch original posts
        List<Post> posts = postRepository.findBySocialMedia_Name(name);
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
            results.add(dto);
        }

        // Fetch reposts
        List<Repost> reposts = repostRepository.findBySocialMediaEntity_Name(name);
        for (Repost r : reposts) {
            PostResponseDTO dto = new PostResponseDTO();
            dto.setPostId(null); // Reposts don't have Post_ID
            dto.setSocialMedia(r.getSocialMediaEntity().getName());
            dto.setUsername(r.getUserEntity().getUsername());
            dto.setTime(r.getId().getTime());
            dto.setRepost(true);
            dto.setOriginalUsername(r.getRepostUserEntity().getUsername());
            dto.setOriginalTime(r.getRepostTime());
            results.add(dto);
        }

        return results;
    }

    @GetMapping("/between")
    public List<PostResponseDTO> getPostsBetweenTimes(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<PostResponseDTO> results = new ArrayList<>();

        List<Post> posts = postRepository.findByTimeBetween(start, end);
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
            results.add(dto);
        }

        List<Repost> reposts = repostRepository.findById_TimeBetween(start, end);
        for (Repost r : reposts) {
            PostResponseDTO dto = new PostResponseDTO();
            dto.setPostId(null);
            dto.setSocialMedia(r.getSocialMediaEntity().getName());
            dto.setUsername(r.getUserEntity().getUsername());
            dto.setTime(r.getId().getTime());
            dto.setRepost(true);
            dto.setOriginalUsername(r.getRepostUserEntity().getUsername());
            dto.setOriginalTime(r.getRepostTime());
            results.add(dto);
        }

        return results;
    }
}
