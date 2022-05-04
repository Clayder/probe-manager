package br.com.elo7.sonda.candidato.api.service;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;

public interface IPlanetService {
    Planet getById(Long id);
    Planet addProbePlanet(IPlanetEntity planet);
    Planet update(Planet planet, Long id);
    Planet updatePlanetSize(Planet planet, Long id);
    void delete(Long id);
}
