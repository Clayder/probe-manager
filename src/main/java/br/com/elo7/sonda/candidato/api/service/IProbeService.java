package br.com.elo7.sonda.candidato.api.service;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;

import java.util.List;

public interface IProbeService {
    List<Probe> convertAndMoveProbes(
            IPlanetEntity planet,
            Planet planetModel
    );

    List<Probe> convertListIProbeEntyToListProbe(List<IProbeEntity> probeEntities);
}
