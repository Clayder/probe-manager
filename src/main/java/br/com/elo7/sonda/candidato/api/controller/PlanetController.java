package br.com.elo7.sonda.candidato.api.controller;

import java.util.List;

import br.com.elo7.sonda.candidato.api.constants.IConstants;
import br.com.elo7.sonda.candidato.api.dto.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.PlanetSchemaDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.service.IPlanetService;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.PlanetEntity;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import br.com.elo7.sonda.candidato.api.service.impl.ProbeService;

@Controller
@RequestMapping(IConstants.Controller.Planet.SLUG_PATH)
public class PlanetController {
	private ProbeService probeService;

	private IPlanetService planetService;

	private ModelMapper modelMapper;

	public PlanetController(
			ProbeService probeService,
			ModelMapper modelMapper,
			IPlanetService planetService
	) {
		this.probeService = probeService;
		this.planetService = planetService;
		this.modelMapper = modelMapper;
	}

	@PostMapping
    public ResponseEntity<PlanetDTO> register(@RequestBody PlanetDTO planetDto) {
		IPlanetEntity planet = modelMapper.map(planetDto, PlanetEntity.class);
		return ResponseEntity.ok(modelMapper.map(this.planetService.addProbePlanet(planet), PlanetDTO.class));
    }

	@GetMapping("{id}")
    public ResponseEntity<PlanetDTO> get(@PathVariable Long id) {
		Planet planet = this.planetService.getById(id);
		return ResponseEntity.ok(modelMapper.map(planet, PlanetDTO.class));
    }

	@PutMapping("{id}")
    public ResponseEntity<PlanetSchemaDTO> update(@PathVariable Long id, @RequestBody PlanetSchemaDTO planetDto) {
		Planet planet = this.planetService.update(this.modelMapper.map(planetDto, Planet.class), id);
		return ResponseEntity.ok(modelMapper.map(planet, PlanetSchemaDTO.class));
    }
}
