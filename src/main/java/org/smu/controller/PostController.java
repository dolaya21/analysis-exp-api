package org.smu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class PostController {
    @GetMapping("/social-media/{id}")
    public void getUserById(@PathVariable String id) {
        System.out.println(id);
    }

    @GetMapping("/{id}")
    public void getUserById(@PathVariable Long id) {
        System.out.println(id);
    }

    @GetMapping("/socailmedia/{socailmedianame}")
    public void getPostBySociaMedia(@PathVariable String socailmedianame) {
        System.out.println(socailmedianame);
    }
}
