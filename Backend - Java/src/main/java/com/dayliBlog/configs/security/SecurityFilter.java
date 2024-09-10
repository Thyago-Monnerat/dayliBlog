package com.dayliBlog.configs.security;

import com.dayliBlog.models.UserModel;
import com.dayliBlog.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;
    private static final Logger LOGGER = Logger.getLogger(SecurityFilter.class.getName());

    /**
     * Filtro que verifica se o usuário está autenticado
     *
     * @param request     Requisição Http
     * @param response    Resposta Http
     * @param filterChain Filtro da cadeia de filtros Http
     * @throws ServletException Trata exceções Servlet
     * @throws IOException      Trata exceções de Entrada e Saída
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = this.recoverToken(request);

        String path = request.getRequestURI();
        if (path.startsWith("/auth/") || path.startsWith("/api/v1/get")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (token != null) {
            String login = tokenService.validateToken(token);
            Optional<UserModel> user = userRepository.findByName(login);
            if (user.isPresent()) {
                List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.get().getRole()));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else {
                LOGGER.warning("Usuario não encontrado");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

        } else {
            LOGGER.warning("Token invalido ou expirado");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token do cabeçalho da requisição
     *
     * @param request é a requisição que será verificada
     * @return O token recuperado da requisição
     */
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }

        return authHeader.replace("Bearer ", "");
    }
}
