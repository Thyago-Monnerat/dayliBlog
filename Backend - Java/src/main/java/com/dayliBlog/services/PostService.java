package com.dayliBlog.services;


import com.dayliBlog.dtos.NewPostDTO;
import com.dayliBlog.exceptions.CannotDeletePostException;
import com.dayliBlog.exceptions.ShortContentException;
import com.dayliBlog.exceptions.ShortTitleException;
import com.dayliBlog.exceptions.TitleAlreadyExistsException;
import com.dayliBlog.models.PostModel;
import com.dayliBlog.models.UserModel;
import com.dayliBlog.repositories.PostRepository;
import com.dayliBlog.util.UserValidationByToken;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserValidationByToken userValidationByToken;

    /**
     * Cria uma nova tarefa.
     *
     * @param auth       Header de autorização JWT
     * @param newPostDTO DTO contendo informações da nova tarefa
     * @return Mensagem de sucesso
     */

    public String createTask(String auth, NewPostDTO newPostDTO) {
        UserModel user = this.userValidationByToken.validation(auth);
        boolean titleExists = postRepository.findByTitle(newPostDTO.title()).isPresent();

        if (titleExists) {
            throw new TitleAlreadyExistsException("Já existe um post com esse título");
        }

        if (newPostDTO.title().length() < 5) {
            throw new ShortTitleException("Título muito curto!");
        }

        if (newPostDTO.content().length() < 100) {
            throw new ShortContentException("Conteúdo muito curto!");
        }

        PostModel newPost = new PostModel();
        newPost.setUserId(user);
        newPost.setTitle(newPostDTO.title());
        newPost.setImgUrl(newPostDTO.imgUrl());
        newPost.setContent(newPostDTO.content());
        newPost.setDate(java.time.LocalDate.now());

        postRepository.save(newPost);

        return "Post criado!";
    }

    /**
     * Retorna todos os Posts do usuário autenticado
     *
     * @return Lista de posts
     */
    public List<PostModel> getAllPosts(String id) {
        UserModel user = this.userValidationByToken.validation(id);
        return user.getPosts();
    }

    /**
     * Deleta um post pelo ID
     *
     * @param id ID do post a ser deletada
     * @return Mensagem de sucesso
     */
    public String deletePost(String id) {
        try {
            postRepository.deletePost(UUID.fromString(id));
            return "Post deletado!";
        }catch (Exception e){
            throw new CannotDeletePostException("Post não encontrado");
        }
    }
}
