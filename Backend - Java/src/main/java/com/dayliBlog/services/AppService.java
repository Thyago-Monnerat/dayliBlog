package com.dayliBlog.services;

import com.dayliBlog.configs.security.TokenService;
import com.dayliBlog.models.PostModel;
import com.dayliBlog.repositories.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AppService {
    private final PostRepository postRepository;
    private final TokenService tokenService;

    /** Para cada post, retorna um JWT
     *
     * @return Lista de JWTs
     */
    public List<String> getInfo() {
        List<PostModel> posts = postRepository.findAll();
        List<String> tokens = new ArrayList<>();

        posts.forEach(post -> tokens.add(tokenService.generatePostToken(post)));
        return tokens;
    }
}
