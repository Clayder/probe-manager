package br.com.elo7.sonda.candidato.api.service.impl;

import br.com.elo7.sonda.candidato.api.model.User;
import br.com.elo7.sonda.candidato.api.repository.IUserRepository;
import br.com.elo7.sonda.candidato.api.service.IUserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private IUserRepository repository;

    public UserService(IUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User getById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new BadCredentialsException(""));
    }
}
