package br.com.elo7.sonda.candidato.api.service;

import br.com.elo7.sonda.candidato.api.config.ApplicationConfig;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.repository.IProbeRepository;
import br.com.elo7.sonda.candidato.api.service.impl.ProbeService;
import br.com.elo7.sonda.candidato.domain.exceptions.type.BusinessException;
import br.com.elo7.sonda.candidato.domain.exceptions.type.ObjectNotFoundException;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.ProbeEntity;
import br.com.elo7.sonda.candidato.fakes.entity.PlanetEntityFake;
import br.com.elo7.sonda.candidato.fakes.model.PlanetFake;
import br.com.elo7.sonda.candidato.fakes.entity.ProbeEntityFake;
import br.com.elo7.sonda.candidato.fakes.model.ProbeFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ApplicationConfig.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProbeServiceTest {

    private IProbeService service;

    @MockBean
    private IProbeRepository repository;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        this.service = new ProbeService(repository, modelMapper);
    }

    @Test
    @DisplayName("Save probe successfully.")
    public void saveProbeSuccessfully() {
        IProbeEntity probeEntity = ProbeEntityFake.create();
        Planet planetModel = PlanetFake.create();

        // Mock
        Probe probeSaved = ProbeFake.create(planetModel);
        probeSaved.setId(1L);

        Mockito.when(repository.save(Mockito.any(Probe.class))).thenReturn(probeSaved);

        // Exec
        Probe probe = this.service.insert(probeEntity, planetModel);

        // Assert
        assertThat(probe.getId()).isEqualTo(probeSaved.getId());
        assertThat(probe.getX()).isEqualTo(probeSaved.getX());
        assertThat(probe.getY()).isEqualTo(probeSaved.getY());
        assertThat(probe.getCreatedAt()).isNotNull();

    }

    @Test
    @DisplayName("Avoid collision between probes.")
    public void avoidCollisionBetweenProbes() {
        IProbeEntity probeEntity = ProbeEntityFake.create();
        Planet planetModel = PlanetFake.create();

        // Mock
        Probe probeSaved = ProbeFake.create(planetModel);
        probeSaved.setId(1L);

        Mockito.when(repository.existsProbeByPlanetAndXAndYAndDeletedAtIsNull(
                Mockito.any(Planet.class),
                Mockito.anyInt(),
                Mockito.anyInt()
        )).thenReturn(true);

        // Assert
        Assertions.assertThrows(
                BusinessException.class, () -> this.service.insert(probeEntity, planetModel));

    }

    @Test
    @DisplayName("Get probe by id.")
    public void getProbeById() {

        // Mock
        Planet planetModel = PlanetFake.create();
        Probe probeSaved = ProbeFake.create(planetModel);
        probeSaved.setId(1L);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(probeSaved));

        // Exec
        Probe probe = this.service.getById(1L);

        // Assert
        assertThat(probe.getId()).isEqualTo(probeSaved.getId());
        assertThat(probe.getX()).isEqualTo(probeSaved.getX());
        assertThat(probe.getY()).isEqualTo(probeSaved.getY());
        assertThat(probe.getCreatedAt()).isNotNull();

    }

    @Test
    @DisplayName("Probe not found by id.")
    public void probeNotFoundById() {

        // Mock
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // Assert
        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> this.service.getById(1L));

    }

    @Test
    @DisplayName("Test move probe.")
    public void testMoveProbe() {

        IPlanetEntity planetEntity = PlanetEntityFake.create();
        ProbeEntity probe1 = ProbeEntityFake.create();
        Probe probeModel = ProbeFake.create(PlanetFake.create());

        // Mock
        Probe probeSaved = ProbeFake.createWithoutPlanet();
        probeSaved.setId(1L);

        Mockito.when(repository.save(Mockito.any(Probe.class))).thenReturn(probeSaved);

        //exec
        Probe probe = this.service.moveProbe(probe1, planetEntity, probeModel);

        // assert
        assertThat(probe1.getX()).isEqualTo(1);
        assertThat(probe1.getY()).isEqualTo(3);

    }

    @Test
    @DisplayName("Test move probe with collision.")
    public void testMoveProbeWithCollision() {

        IPlanetEntity planetEntity = PlanetEntityFake.create();
        ProbeEntity probe1 = ProbeEntityFake.create();
        Probe probeModel = ProbeFake.create(PlanetFake.create());

        // Mock
        Probe probeSaved = ProbeFake.createWithoutPlanet();
        probeSaved.setId(1L);

        Mockito.when(repository.existsProbeByPlanetAndXAndYAndIdNotAndDeletedAtIsNull(
                Mockito.any(Planet.class),
                Mockito.anyInt(),
                Mockito.anyInt(),
                Mockito.anyLong()
        )).thenReturn(true);

        Mockito.when(repository.save(Mockito.any(Probe.class))).thenReturn(probeSaved);

        // Assert
        Assertions.assertThrows(
                BusinessException.class, () -> this.service.moveProbe(probe1, planetEntity, probeModel));
    }




}
