package com.dayliBlog.util;

import com.dayliBlog.configs.security.TokenService;
import com.dayliBlog.exceptions.CannotValidateUserException;
import com.dayliBlog.models.UserModel;
import com.dayliBlog.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserValidationByToken {

    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserModel validation(String auth) throws CannotValidateUserException {
        String token = auth.replace("Bearer ", "");
        String tokenName = this.tokenService.decodeToken(token).get("sub").toString().replace("\"", "");

        return this.userRepository.findById(UUID.fromString(tokenName))
                .orElseThrow(() -> new CannotValidateUserException("Usuário não pode ser validado!"));
    }
}
