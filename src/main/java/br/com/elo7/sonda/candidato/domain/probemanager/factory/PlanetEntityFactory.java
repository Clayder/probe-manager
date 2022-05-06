package br.com.elo7.sonda.candidato.domain.probemanager.factory;

import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.PlanetEntity;

abstract public class PlanetEntityFactory {
    public static IPlanetEntity create(Long id, String name, int width, int height) {
        return new PlanetEntity(id, name, width, height);
    }
}
