package org.smu.controller;

import org.smu.database.entity.Post;
import org.smu.database.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
