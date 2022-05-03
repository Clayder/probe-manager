package br.com.elo7.sonda.candidato.service;


import java.util.List;
import java.util.stream.Collectors;


import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import org.springframework.stereotype.Service;

@Service
public class ProbeService {

	public List<IProbeEntity> landProbes(IPlanetEntity planet) {
		List<IProbeEntity> probeEntities = planet.getProbes();
		return convertAndMoveProbes(planet, probeEntities);
	}

	private List<IProbeEntity> convertAndMoveProbes(IPlanetEntity planet, List<IProbeEntity> probeEntities) {
		return probeEntities
			.stream().map(
					probeEntity -> {
						probeEntity.setPlanetEntity(planet);
						moveProbeWithAllCommands(probeEntity);
						return probeEntity;
					}
			).collect(Collectors.toList());
	}

	private void moveProbeWithAllCommands(IProbeEntity probeEntity) {
		for (char command : probeEntity.getCommands().toCharArray()) {
			probeEntity.applyCommandToProbe(command);
		}
	}

}
