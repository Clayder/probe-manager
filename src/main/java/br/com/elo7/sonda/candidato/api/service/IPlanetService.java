package br.com.elo7.sonda.candidato.api.service;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;

public interface IPlanetService {
    Planet insert(IPlanetEntity planetEntity);
}
