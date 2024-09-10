package com.dayliBlog.configs.security;

import com.dayliBlog.models.PostModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.dayliBlog.models.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;


@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * Gera um token para um usuário
     *
     * @param user indica o usuário que receberá o token
     * @return O token gerado
     */
    public String generateToken(UserModel user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("blog")
                    .withSubject(user.getId().toString())
                    .withClaim("profilePic", user.getProfilePic())
                    .withClaim("name", user.getUsername())
                    .withClaim("role", user.getRole().toString())
                    .withExpiresAt(this.generateInstant())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token ", e);
        }
    }

    /**
     * Gera um token para um post
     *
     * @param post indica o post que receberá o token
     * @return O token gerado
     */
    public String generatePostToken(PostModel post) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secret);

            return JWT.create()
                    .withIssuer("blog")
                    .withSubject(post.getId().toString())
                    .withClaim("title", post.getTitle())
                    .withClaim("content", post.getContent())
                    .withClaim("imgUrl", post.getImgUrl())
                    .withClaim("name", post.getUserId())
                    .withClaim("date", post.getDate().toString())
                    .withExpiresAt(this.generateInstant())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token ", e);
        }
    }

    /**
     * Valida um JWT Token
     *
     * @param token O token a ser validado
     * @return O nome do usuário que o token pertence
     * @throws JWTVerificationException Caso o token seja inválido
     */
    public String validateToken(String token) {

        Algorithm algorithm = Algorithm.HMAC256(this.secret);

        return JWT.require(algorithm)
                .withIssuer("blog")
                .build()
                .verify(token)
                .getClaim("name").toString().replace("\"", "");
    }

    /**
     * Gera um instante para expirar o token
     *
     * @return O instante gerado
     * @throws JWTVerificationException Caso o token seja inválido
     */
    private Instant generateInstant() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    /**
     * Decodifica um token JWT
     *
     * @param token  O token a ser decodificado
     * @return O token decodificado
     * @throws JWTVerificationException Caso o token seja inválido
     */

    public Map<String, Claim> decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.secret);

        return JWT.require(algorithm)
                .withIssuer("blog")
                .build()
                .verify(token)
                .getClaims();

    }
}
