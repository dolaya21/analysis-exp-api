package org.smu.service;

import lombok.AllArgsConstructor;
import org.smu.database.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
}
