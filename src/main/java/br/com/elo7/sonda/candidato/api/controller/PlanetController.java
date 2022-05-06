package br.com.elo7.sonda.candidato.api.controller;

import br.com.elo7.sonda.candidato.api.constants.IConstants;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetSchemaDTO;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetWidthHeightDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.service.IPlanetService;
import br.com.elo7.sonda.candidato.domain.exceptions.controller.StandardError;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.PlanetEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Message.DefaultHttp.*;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Message.Planet.*;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Planet.PATH;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Planet.PLANET_POBE;


@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping(PATH)
@Tag(name = "Planeta")
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "500", description = DESCRIPTION_500, content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = StandardError.class))
                    })
            }
    )
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

    @Operation(summary = SUMMARY_REGISTER)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201", description = DESCRIPTION_REGISTER_201,
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PlanetDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = DESCRIPTION_400, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "422", description = DESCRIPTION_422, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
            }
    )
    @PostMapping(IConstants.Controller.Probe.NAME)
    public ResponseEntity<PlanetDTO> register(@Valid @RequestBody PlanetDTO planetDto) {
        IPlanetEntity planet = modelMapper.map(planetDto, PlanetEntity.class);
        return new ResponseEntity(modelMapper.map(this.planetService.addProbePlanet(planet), PlanetDTO.class), HttpStatus.CREATED);
    }

    @Operation(summary = SUMMARY_INSERT)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201", description = DESCRIPTION_201,
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PlanetDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = DESCRIPTION_400, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "422", description = DESCRIPTION_422, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PlanetSchemaDTO> insert(@Valid @RequestBody PlanetSchemaDTO planetDto) {
        Planet planet = modelMapper.map(planetDto, Planet.class);
        return new ResponseEntity(modelMapper.map(this.planetService.insert(planet), PlanetSchemaDTO.class), HttpStatus.CREATED);
    }

    @Operation(summary = SUMMARY_GET)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = DESCRIPTION_200,
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PlanetDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "404", description = DESCRIPTION_404, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    })
            }
    )
    @GetMapping("{id}")
    public ResponseEntity<PlanetDTO> get(@PathVariable Long id) {
        Planet planet = this.planetService.getById(id);
        return ResponseEntity.ok(modelMapper.map(planet, PlanetDTO.class));
    }

    @Operation(summary = SUMMARY_UPDATE)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = DESCRIPTION_200,
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PlanetDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = DESCRIPTION_400, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "422", description = DESCRIPTION_422, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = DESCRIPTION_404, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<PlanetSchemaDTO> update(@PathVariable Long id, @Valid @RequestBody PlanetSchemaDTO planetDto) {
        Planet planet = this.planetService.update(this.modelMapper.map(planetDto, Planet.class), id);
        return ResponseEntity.ok(modelMapper.map(planet, PlanetSchemaDTO.class));
    }

    @Operation(summary = SUMMARY_PATCH_WIDTH_HEIGHT)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = DESCRIPTION_200,
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PlanetDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = DESCRIPTION_400, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "422", description = DESCRIPTION_422, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = DESCRIPTION_404, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
            }
    )
    @PatchMapping("{id}")
    public ResponseEntity<PlanetSchemaDTO> patchWidthHeight(@PathVariable Long id, @Valid @RequestBody PlanetWidthHeightDTO dto) {
        Planet planet = this.planetService.updatePlanetSize(this.modelMapper.map(dto, Planet.class), id);
        return ResponseEntity.ok(modelMapper.map(planet, PlanetSchemaDTO.class));
    }

    @Operation(summary = SUMMARY_DELETE)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204", description = DESCRIPTION_204,
                            content = @Content
                    ),
                    @ApiResponse(responseCode = "400", description = DESCRIPTION_400, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "422", description = DESCRIPTION_422, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = DESCRIPTION_404, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
            }
    )
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.planetService.delete(id);
    }


    @Operation(summary = SUMMARY_LIST)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200", description = DESCRIPTION_200,
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PlanetDTO.class))
                            }
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<Page<Planet>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limitPerPage", defaultValue = "10") Integer limitPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort) {

        Page<Planet> list = this.planetService.findPage(page, limitPerPage, orderBy, sort);
        return ResponseEntity.ok().body(list);
    }

    @Operation(summary = SUMMARY_REGISTER_PROBE_BY_PLANET)
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201", description = DESCRIPTION_201,
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = PlanetDTO.class))
                            }
                    ),
                    @ApiResponse(responseCode = "400", description = DESCRIPTION_400, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "404", description = DESCRIPTION_REGISTER_PROBE_BY_PLANET_404, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
                    @ApiResponse(responseCode = "422", description = DESCRIPTION_422, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
            }
    )
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
