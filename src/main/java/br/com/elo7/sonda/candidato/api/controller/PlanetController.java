package br.com.elo7.sonda.candidato.api.controller;

import br.com.elo7.sonda.candidato.api.constants.IConstants;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetSchemaDTO;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetWidthHeightDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.service.IPlanetService;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.PlanetEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Planet.PATH;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Planet.PLANET_POBE;


@Controller
@RequestMapping(PATH)
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

    @PostMapping(IConstants.Controller.Probe.NAME)
    public ResponseEntity<PlanetDTO> register(@Valid @RequestBody PlanetDTO planetDto) {
        IPlanetEntity planet = modelMapper.map(planetDto, PlanetEntity.class);
        return ResponseEntity.ok(modelMapper.map(this.planetService.addProbePlanet(planet), PlanetDTO.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlanetSchemaDTO> insert(@Valid @RequestBody PlanetSchemaDTO planetDto) {
        Planet planet = modelMapper.map(planetDto, Planet.class);
        return new ResponseEntity(modelMapper.map(this.planetService.insert(planet), PlanetSchemaDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlanetDTO> get(@PathVariable Long id) {
        Planet planet = this.planetService.getById(id);
        return ResponseEntity.ok(modelMapper.map(planet, PlanetDTO.class));
    }

    @PutMapping("{id}")
    public ResponseEntity<PlanetSchemaDTO> update(@PathVariable Long id, @Valid @RequestBody PlanetSchemaDTO planetDto) {
        Planet planet = this.planetService.update(this.modelMapper.map(planetDto, Planet.class), id);
        return ResponseEntity.ok(modelMapper.map(planet, PlanetSchemaDTO.class));
    }

    @PatchMapping("{id}")
    public ResponseEntity<PlanetSchemaDTO> patchWidthHeight(@PathVariable Long id, @Valid @RequestBody PlanetWidthHeightDTO dto) {
        Planet planet = this.planetService.updatePlanetSize(this.modelMapper.map(dto, Planet.class), id);
        return ResponseEntity.ok(modelMapper.map(planet, PlanetSchemaDTO.class));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.planetService.delete(id);
    }

    @GetMapping()
    public ResponseEntity<Page<Planet>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limitPerPage", defaultValue = "10") Integer limitPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort) {

        Page<Planet> list = this.planetService.findPage(page, limitPerPage, orderBy, sort);
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(PLANET_POBE)
    public ResponseEntity<PlanetDTO> registerProbeByPlanet(
            @PathVariable Long id,
            @Valid @RequestBody ProbeDTO dto
    ) {
        List<IProbeEntity> probeList = new ArrayList<>();
        probeList.add(modelMapper.map(dto, IProbeEntity.class));

        Planet planetModel = this.planetService.getById(id);

        IPlanetEntity planetEntity = modelMapper.map(planetModel, IPlanetEntity.class);
        planetEntity.setProbes(probeList);

        return ResponseEntity.ok(modelMapper.map(this.planetService.addProbeByPlanet(planetEntity, planetModel), PlanetDTO.class));
    }
}
