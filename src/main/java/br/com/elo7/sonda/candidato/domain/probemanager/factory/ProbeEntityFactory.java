package br.com.elo7.sonda.candidato.domain.probemanager.factory;

import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.ProbeEntity;

abstract public class ProbeEntityFactory {
    public static IProbeEntity create(int x, int y, char direction, String commands) {
        return new ProbeEntity(x, y, direction, commands);
    }
}
