package br.com.elo7.sonda.candidato.api.controller;

import java.util.List;

import br.com.elo7.sonda.candidato.api.constants.IConstants;
import br.com.elo7.sonda.candidato.api.dto.PlanetDTO;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.PlanetEntity;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.elo7.sonda.candidato.api.service.impl.ProbeService;

@Controller
@RequestMapping(IConstants.Controller.Planet.SLUG_PATH)
public class PlanetController {
	private ProbeService probeService;

	private ModelMapper modelMapper;

	public PlanetController(ProbeService probeService, ModelMapper modelMapper) {
		this.probeService = probeService;
		this.modelMapper = modelMapper;
	}

	@PostMapping
    public ResponseEntity<PlanetDTO> register(@RequestBody PlanetDTO planetDto) {
		IPlanetEntity planet = modelMapper.map(planetDto, PlanetEntity.class);
		List<IProbeEntity> probeEntities = probeService.landProbes(planet);
		planet.setProbes(probeEntities);

		PlanetDTO newPlanetDto = modelMapper.map(planet, PlanetDTO.class);
		return ResponseEntity.ok(newPlanetDto);
    }
}
