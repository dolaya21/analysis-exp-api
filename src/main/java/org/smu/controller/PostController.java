package org.smu.controller;

import org.smu.database.entity.Post;
import org.smu.database.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostRepository repository;

    @GetMapping("/by-social-media/{name}")
    public List<Post> getPostsBySocialMedia(@PathVariable String name) {
        return repository.findBySocialMedia_Name(name);
    }

    @GetMapping("/between")
    public List<Post> getPostsBetweenTimes(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return repository.findByTimeBetween(start, end);
    }
}
