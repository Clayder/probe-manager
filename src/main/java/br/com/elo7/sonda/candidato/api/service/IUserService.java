package br.com.elo7.sonda.candidato.api.service;

import br.com.elo7.sonda.candidato.api.model.User;

public interface IUserService {
    User getById(Long id);
}
