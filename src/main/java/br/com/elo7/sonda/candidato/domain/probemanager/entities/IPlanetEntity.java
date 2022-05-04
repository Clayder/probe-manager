package br.com.elo7.sonda.candidato.domain.probemanager.entities;

import java.util.List;

public interface IPlanetEntity {
    List<IProbeEntity> getProbes();

    void setProbes(List<IProbeEntity> probes);

    int getWidth();

    int getHeight();

    void setId(Long id);
}
