package br.com.elo7.sonda.candidato.api.config.security;

import br.com.elo7.sonda.candidato.api.model.User;
import br.com.elo7.sonda.candidato.api.service.ITokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Executes before each request
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private ITokenService tokenService;

    public AuthenticationTokenFilter(ITokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        boolean isValid = tokenService.isTokenValid(token);

        if (isValid) {
            authenticateUser(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        User user = tokenService.getUser(token);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,null, user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }

        // Remove "Bearer "
        return token.replace("Bearer", "").trim();
    }
}
