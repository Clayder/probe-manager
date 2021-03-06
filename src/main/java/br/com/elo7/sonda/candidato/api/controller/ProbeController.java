package br.com.elo7.sonda.candidato.api.controller;

import br.com.elo7.sonda.candidato.api.dto.planet.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.MoveProbeDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.service.IProbeService;
import br.com.elo7.sonda.candidato.domain.exceptions.controller.StandardError;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.factory.PlanetEntityFactory;
import br.com.elo7.sonda.candidato.domain.probemanager.factory.ProbeEntityFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Message.DefaultHttp.*;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Message.DefaultHttp.DESCRIPTION_404;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Message.Probe.*;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Probe.PATH;

@RestController
@RequestMapping(PATH)
@Tag(name = "Sonda")
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "500", description = DESCRIPTION_500, content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = StandardError.class))
                })
        }
)
@SecurityRequirement(name = "bearerAuth")
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

    @Operation(summary = SUMMARY_MOVE_PROBE)
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
                    @ApiResponse(responseCode = "404", description = DESCRIPTION_MOVE_PROBE_404, content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = StandardError.class))
                    }),
            }
    )
    @PutMapping("{id}")
    public ResponseEntity<ProbeDTO> moveProbe(@PathVariable Long id, @Valid @RequestBody MoveProbeDTO dto) {
        Probe probeModel = this.probeService.getById(id);
        IProbeEntity probeEntity = ProbeEntityFactory.create(probeModel.getX(), probeModel.getY(), probeModel.getDirection(), dto.getCommands());
        probeEntity.setId(probeModel.getId());

        Planet planetModel = probeModel.getPlanet();
        IPlanetEntity planetEntity = PlanetEntityFactory.create(planetModel.getId(), planetModel.getName(), planetModel.getWidth(), planetModel.getHeight());

        Probe probe = this.probeService.moveProbe(probeEntity, planetEntity, probeModel);

        return ResponseEntity.ok(this.modelMapper.map(probe, ProbeDTO.class));
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
        this.probeService.delete(id);
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
    public ResponseEntity<ProbeDTO> get(@PathVariable Long id) {
        Probe probe = this.probeService.getById(id);
        return ResponseEntity.ok(modelMapper.map(probe, ProbeDTO.class));
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
    public ResponseEntity<Page<Probe>> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limitPerPage", defaultValue = "10") Integer limitPerPage,
            @RequestParam(value = "orderBy", defaultValue = "direction") String orderBy,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort) {

        Page<Probe> list = this.probeService.findPage(page, limitPerPage, orderBy, sort);
        return ResponseEntity.ok().body(list);
    }


}
