package com.dayliBlog.controllers;

import com.dayliBlog.dtos.NewPostDTO;
import com.dayliBlog.models.PostModel;
import com.dayliBlog.services.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * Cria um novo Post
     *
     * @param auth       Header de autorização JWT
     * @param newPostDTO Título do Post,  descrição e url da imagem
     * @return ResponseEntity com a mensagem de sucesso ou erro
     */
    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestHeader("Authorization") String auth, @RequestBody NewPostDTO newPostDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.createTask(auth, newPostDTO));
    }

    /**
     * Retorna todas as tarefas do usuário autenticado
     *
     * @return ResponseEntity com uma lista de tarefas
     */

    @GetMapping("/all")
    public ResponseEntity<List<PostModel>> getAllPosts(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts(auth));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.deletePost(id));
    }

}
