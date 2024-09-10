package com.dayliBlog.services;

import com.dayliBlog.configs.security.TokenService;
import com.dayliBlog.dtos.NewPicDTO;
import com.dayliBlog.dtos.UserChangePasswordDTO;
import com.dayliBlog.dtos.UserLoginDTO;
import com.dayliBlog.dtos.UserRegisterDTO;
import com.dayliBlog.enums.Role;
import com.dayliBlog.exceptions.*;
import com.dayliBlog.models.UserModel;
import com.dayliBlog.repositories.PostRepository;
import com.dayliBlog.repositories.UserRepository;
import com.dayliBlog.util.IsAdmin;
import com.dayliBlog.util.UserValidationByToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PostRepository  postRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserValidationByToken userValidationByToken;
    private final IsAdmin isAdmin;

    /**
     * Verifica as credenciais do usuário e gera um token se forem válidas.
     *
     * @param userLoginDTO DTO contendo nome de usuário e senha.
     * @return Token JWT gerado para o usuário.
     * @throws UsernameNotFoundException Se as credenciais forem inválidas.
     */
    public String login(UserLoginDTO userLoginDTO) {
        Optional<UserModel> user = this.userRepository.findByName(userLoginDTO.username());

        if (user.isPresent() && this.passwordEncoder.matches(userLoginDTO.password(), user.get().getPassword())) {
            return this.tokenService.generateToken(user.get());
        } else {
            throw new UsernameNotFoundException("Credenciais inválidas");
        }
    }

    /**
     * Registra um novo usuário.
     *
     * @param userRegisterDTO DTO contendo nome de usuário e senha.
     * @return Mensagem de sucesso ou erro.
     * @throws ShortUsernameException Se o nome de usuário ou senha forem muito curtos.
     * @throws InvalidSpecialCharactersException Se o nome de usuário conter caracteres especiais ou espaços.
     * @throws InvalidFirstCharacterException Se o primeiro caractere do nome de usuário não for uma letra.
     */
    public String register(UserRegisterDTO userRegisterDTO) {
        Optional<UserModel> user = this.userRepository.findByName(userRegisterDTO.username());

        if(userRegisterDTO.username().length()  < 3 || userRegisterDTO.password().length()  < 3){
            throw new ShortUsernameException("Credenciais devem conter no mínimo 3 letras.");
        }

        if (userRegisterDTO.username().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?`~ \\d].*")) {
            throw new InvalidSpecialCharactersException("Nome de usuário não pode conter caracteres especiais, espaços ou números.");
        }

        if(!Character.isLetter(userRegisterDTO.username().charAt(0))){
            throw new InvalidFirstCharacterException("O primeiro caractere do nome de usuário deve ser uma letra.");
        }

        if (user.isEmpty()) {
            String encryptedPassword = this.passwordEncoder.encode(userRegisterDTO.password());

            UserModel newUser = new UserModel();

            if (isAdmin.checkAdmin(userRegisterDTO.username())) {
                newUser.setRole(Role.ADMIN);
            }else{
                newUser.setRole(Role.USER);
            }

            newUser.setUsername(userRegisterDTO.username());
            newUser.setPassword(encryptedPassword);


            this.userRepository.save(newUser);

            return "Usuário criado com sucesso!";
        } else {
            throw new UserAlreadyExistsException("Nome já existente");
        }
    }

    /**
     * Deleta a conta do usuário autenticado.
     *
     * @param auth Token de autenticação do usuário.
     * @return Mensagem de sucesso ou erro.
     * @throws CannotValidateUserException Se o token de autenticação for inválido.
     */
    public String deleteMyAccount(String auth) {
        UserModel user = this.userValidationByToken.validation(auth);
        this.userRepository.delete(user);
        return "Usuário deletado com sucesso!";
    }

    /**
     * Retorna os dados do usuário autenticado.
     *
     * @param auth Token de autenticação do usuário.
     * @return Dados do usuário.
     * @throws CannotValidateUserException Se o token de autenticação for inválido.
     */
    public UserModel getMyAccount(String auth) {
        return this.userValidationByToken.validation(auth);
    }

    /**
     * Altera a senha do usuário autenticado.
     *
     * @param auth                  Token de autenticação do usuário.
     * @param userChangePasswordDTO DTO contendo a senha antiga e nova senha.
     * @return Mensagem de sucesso ou erro.
     * @throws DifferentPasswordsException Se a nova senha for igual à antiga ou as senhas não coincidirem.
     * @throws CannotValidateUserException Se o token de autenticação for inválido.
     */
    public String changeMyPassword(String auth, UserChangePasswordDTO userChangePasswordDTO) {

        UserModel user = this.userValidationByToken.validation(auth);

        if(userChangePasswordDTO.newPassword().isEmpty() || userChangePasswordDTO.confirmPassword().isEmpty()){
            throw new EmptyPasswordException("Senha não pode ser vazia!");
        }

        boolean oldPasswordEqualsNew = this.passwordEncoder.matches(userChangePasswordDTO.newPassword(), user.getPassword());
        boolean passwordsMatches = userChangePasswordDTO.newPassword().equals(userChangePasswordDTO.confirmPassword());

        if (oldPasswordEqualsNew) {
            throw new DifferentPasswordsException("Sua nova senha não pode ser igual a antiga!");
        }
        if (!passwordsMatches) {
            throw new DifferentPasswordsException("Senhas não coincidem!");
        }

        String encryptedPassword = this.passwordEncoder.encode(userChangePasswordDTO.newPassword());
        user.setPassword(encryptedPassword);

        this.userRepository.save(user);
        return "Senha alterada com sucesso!";
    }

    /**
     * Retorna todos os usuários cadastrados.
     *
     * @return Lista de usuários.
     */
    public List<UserModel> getAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * Retorna um usuário pelo ID.
     *
     * @param id ID do usuário a ser retornado.
     * @return Usuário encontrado.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     */

    public UserModel getUser(String id) {
        Optional<UserModel> user = this.userRepository.findById(UUID.fromString(id));

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException("Usuário não encontrado");
        }
    }

    /**
     * Deleta um usuário pelo ID.
     *
     * @param id ID do usuário a ser deletado.
     * @return Mensagem de sucesso ou erro.
     * @throws UserNotFoundException Se o usuário não for encontrado.
     */
    public String deleteUser(String id) {
        if (userRepository.existsById(UUID.fromString(id))) {
            this.userRepository.deleteById(UUID.fromString(id));
            return "Usuário deletado com sucesso!";
        } else {
            throw new UserNotFoundException("Usuário não encontrado");
        }
    }

    /**
     * Deleta um post pelo ID.
     *
     * @param id ID do post a ser deletado.
     * @return Mensagem de sucesso ou erro.
     * @throws PostNotFoundException Se o post não for encontrado.
     */
    public String deletePost(String id) {
        if((postRepository.existsById(UUID.fromString(id)))) {
            this.postRepository.deleteById(UUID.fromString(id));
            return "Postagem deletada com sucesso!";
        } else {
            throw new PostNotFoundException("Postagem não encontrada");
        }
    }

    /**
     * Altera a foto de perfil do usuário autenticado.
     *
     * @param auth Token de autenticação do usuário.
     * @param newPicDTO DTO contendo a nova foto de perfil.
     * @return Token JWT atualizado para o usuário.
     * @throws CannotValidateUserException Se o token de autenticação for inválido.
     * */

    public String changeMyPic(String auth, NewPicDTO newPicDTO) {
        UserModel user = this.userValidationByToken.validation(auth);
        user.setProfilePic(newPicDTO.newPic());
        this.userRepository.save(user);
        return tokenService.generateToken(user);
    }
}