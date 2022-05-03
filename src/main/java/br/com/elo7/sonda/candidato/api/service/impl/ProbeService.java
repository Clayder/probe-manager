package br.com.elo7.sonda.candidato.api.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.repository.IProbeRepository;
import br.com.elo7.sonda.candidato.api.service.IProbeService;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

@Service
public class ProbeService implements IProbeService {

	private IProbeRepository probeRepository;

	private ModelMapper modelMapper;

	public ProbeService(IProbeRepository probeRepository, ModelMapper modelMapper) {
		this.probeRepository = probeRepository;
		this.modelMapper = modelMapper;
	}

	public List<IProbeEntity> convertAndMoveProbes(IPlanetEntity planetEntity, Planet planetModel) {
		return planetEntity.getProbes()
				.stream().map(
						probeEntity -> {
							moveProbeWithAllCommands(probeEntity, planetEntity);
							Probe probeModel = this.insert(probeEntity, planetModel);
							probeModel.setId(probeModel.getId());
							return probeEntity;
						}
				).collect(Collectors.toList());
	}

	public Probe insert(IProbeEntity probeEntity, Planet planetModel) {
		Probe probe = modelMapper.map(probeEntity, Probe.class);
		probe.setPlanet(planetModel);
		return probeRepository.save(probe);
	}

	public List<Probe> convertListIProbeEntyToListProbe(List<IProbeEntity> probeEntities) {
		return modelMapper.map(probeEntities, new TypeToken<List<Probe>>() {}.getType());
	}

	private void moveProbeWithAllCommands(IProbeEntity probeEntity, IPlanetEntity planetEntity) {
		for (char command : probeEntity.getCommands().toCharArray()) {
			probeEntity.applyCommandToProbe(command, planetEntity);
		}
	}

}
