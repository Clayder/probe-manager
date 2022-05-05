package br.com.elo7.sonda.candidato.api.service;

import br.com.elo7.sonda.candidato.api.dto.probe.MoveProbeDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProbeService {
    List<Probe> convertAndMoveProbes(
            IPlanetEntity planet,
            Planet planetModel
    );
    List<Probe> convertListIProbeEntyToListProbe(List<IProbeEntity> probeEntities);
    Probe getById(Long id);
    void delete(Long id);

    Probe moveProbe(IProbeEntity probeEntity, IPlanetEntity planetEntity, Probe probeModel);

    Page<Probe> findPage(Integer page, Integer limitPerPage, String orderBy, String sort);

    Probe insert(IProbeEntity probeEntity, Planet planetModel);
}
