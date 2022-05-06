package br.com.elo7.sonda.candidato.api.repository;

import br.com.elo7.sonda.candidato.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository  extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
