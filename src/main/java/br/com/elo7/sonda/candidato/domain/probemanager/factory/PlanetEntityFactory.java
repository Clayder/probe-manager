package br.com.elo7.sonda.candidato.domain.probemanager.factory;

import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.PlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.ProbeEntity;
import lombok.NonNull;

abstract public class PlanetEntityFactory {
    public static IPlanetEntity create(Long id, String name, int width, int height) {
        return new PlanetEntity(id, name, width, height);
    }
}
