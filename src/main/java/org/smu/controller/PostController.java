package org.smu.controller;

import org.smu.database.repository.PostRepository;
import org.smu.database.repository.RepostRepository;
import org.smu.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.smu.util.PostUtils.mapPostsToDTOs;
import static org.smu.util.PostUtils.mapRepostsToDTOs;

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
        results.addAll(mapPostsToDTOs(postRepository.findBySocialMedia_Name(name)));
        results.addAll(mapRepostsToDTOs(repostRepository.findBySocialMediaEntity_Name(name)));
        return results;
    }

    @GetMapping("/between")
    public List<PostResponseDTO> getPostsBetweenTimes(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        List<PostResponseDTO> results = new ArrayList<>();
        results.addAll(mapPostsToDTOs(postRepository.findByTimeBetween(start, end)));
        results.addAll(mapRepostsToDTOs(repostRepository.findById_TimeBetween(start, end)));
        return results;
    }

    @GetMapping("/by-user-and-media")
    public List<PostResponseDTO> getPostsByUserAndMedia(
            @RequestParam("username") String username,
            @RequestParam("media") String media
    ) {
        List<PostResponseDTO> results = new ArrayList<>();
        results.addAll(mapPostsToDTOs(postRepository.findByUser_UsernameAndSocialMedia_Name(username, media)));
        results.addAll(mapRepostsToDTOs(repostRepository.findByUserEntity_UsernameAndSocialMediaEntity_Name(username, media)));
        return results;
    }

    @GetMapping("/by-name")
    public List<PostResponseDTO> getPostsByName(@RequestParam("name") String name) {
        List<PostResponseDTO> results = new ArrayList<>();
        results.addAll(mapPostsToDTOs(
                postRepository.findByUser_FirstNameIgnoreCaseOrUser_LastNameIgnoreCase(name, name))
        );
        results.addAll(mapRepostsToDTOs(
                repostRepository.findByUserEntity_FirstNameIgnoreCaseOrUserEntity_LastNameIgnoreCase(name, name))
        );
        return results;
    }
}
