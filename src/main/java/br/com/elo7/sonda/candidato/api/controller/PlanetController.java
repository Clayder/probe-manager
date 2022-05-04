package br.com.elo7.sonda.candidato.api.controller;

import br.com.elo7.sonda.candidato.api.constants.IConstants;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetSchemaDTO;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetWidthHeightDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.service.IPlanetService;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.PlanetEntity;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(IConstants.Controller.Planet.SLUG_PATH)
public class PlanetController {

	private IPlanetService planetService;

	private ModelMapper modelMapper;

	public PlanetController(
			ModelMapper modelMapper,
			IPlanetService planetService
	) {
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

	@PatchMapping("{id}")
    public ResponseEntity<PlanetSchemaDTO> patchWidthHeight(@PathVariable Long id, @RequestBody PlanetWidthHeightDTO dto) {
		Planet planet = this.planetService.updatePlanetSize(this.modelMapper.map(dto, Planet.class), id);
		return ResponseEntity.ok(modelMapper.map(planet, PlanetSchemaDTO.class));
    }
}
