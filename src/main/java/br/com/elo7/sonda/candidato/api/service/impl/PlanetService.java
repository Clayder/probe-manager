package br.com.elo7.sonda.candidato.api.service.impl;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.repository.IPlanetRepository;
import br.com.elo7.sonda.candidato.api.service.IPlanetService;
import br.com.elo7.sonda.candidato.api.service.IProbeService;
import br.com.elo7.sonda.candidato.domain.exceptions.messages.ErrorMessage;
import br.com.elo7.sonda.candidato.domain.exceptions.type.ObjectNotFoundException;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanetService implements IPlanetService {

    private IPlanetRepository planetRepository;
    private ModelMapper modelMapper;

    private IProbeService probeService;

    public PlanetService(IPlanetRepository planetRepository, ModelMapper modelMapper, IProbeService probeService) {
        this.planetRepository = planetRepository;
        this.modelMapper = modelMapper;
        this.probeService = probeService;
    }

    /**
     * Add probe on planet
     */
    public IPlanetEntity addProbePlanet(IPlanetEntity planetEntity) {
        Planet planetModel = this.insert(planetEntity);
        List<IProbeEntity> probeEntityList = this.probeService.convertAndMoveProbes(
                planetEntity, planetModel
        );
        planetModel.setProbes(this.probeService.convertListIProbeEntyToListProbe(probeEntityList));
        planetRepository.save(planetModel);

		return planetEntity;
	}

    public Planet insert(IPlanetEntity planetEntity) {
        Planet planet = modelMapper.map(planetEntity, Planet.class);
        planet.setId(null);
        return planetRepository.save(planet);
    }

    public Planet getById(Long id) {
        Optional<Planet> obj = planetRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.PLANET_NOT_FOUND));
    }
}
