package br.com.elo7.sonda.candidato.api.repository;

import br.com.elo7.sonda.candidato.api.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlanetRepository extends JpaRepository<Planet, Long> {
    Boolean existsPlanetByName(String name);
    Boolean existsPlanetByNameAndIdNot(String name, Long id);

}
