package br.com.elo7.sonda.candidato.api.controller;

import br.com.elo7.sonda.candidato.api.dto.planet.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetSchemaDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.MoveProbeDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.service.IProbeService;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.factory.PlanetEntityFactory;
import br.com.elo7.sonda.candidato.domain.probemanager.factory.ProbeEntityFactory;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Probe.PATH;

@Controller
@RequestMapping(PATH)
public class ProbeController {

    private IProbeService probeService;

    private ModelMapper modelMapper;

    public ProbeController(
            ModelMapper modelMapper,
            IProbeService probeService
    ) {
        this.probeService = probeService;
        this.modelMapper = modelMapper;
    }


    @PutMapping("{id}")
    public ResponseEntity<ProbeDTO> moveProbe(@PathVariable Long id, @Valid @RequestBody MoveProbeDTO dto) {
        Probe probeModel = this.probeService.getById(id);
        IProbeEntity probeEntity = ProbeEntityFactory.create(probeModel.getX(), probeModel.getY(), probeModel.getDirection(), dto.getCommands());

        Planet planetModel = probeModel.getPlanet();
        IPlanetEntity planetEntity = PlanetEntityFactory.create(planetModel.getId(), planetModel.getName(), planetModel.getWidth(), planetModel.getHeight());

        Probe probe = this.probeService.moveProbe(probeEntity, planetEntity, probeModel);

        return ResponseEntity.ok(this.modelMapper.map(probe, ProbeDTO.class));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.probeService.delete(id);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProbeDTO> get(@PathVariable Long id) {
        Probe probe = this.probeService.getById(id);
        return ResponseEntity.ok(modelMapper.map(probe, ProbeDTO.class));
    }

    @GetMapping()
    public ResponseEntity<Page<Probe>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limitPerPage", defaultValue = "10") Integer limitPerPage,
            @RequestParam(value = "orderBy", defaultValue = "direction") String orderBy,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort) {

        Page<Probe> list = this.probeService.findPage(page, limitPerPage, orderBy, sort);
        return ResponseEntity.ok().body(list);
    }


}
