package br.com.elo7.sonda.candidato.api.service;

import br.com.elo7.sonda.candidato.api.model.User;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface ITokenService {
    String createToken(Authentication authentication);

    boolean isTokenValid(String token);

    User getUser(String token);
}
