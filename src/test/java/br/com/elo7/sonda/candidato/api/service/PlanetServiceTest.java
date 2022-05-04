package br.com.elo7.sonda.candidato.api.service;

import br.com.elo7.sonda.candidato.api.config.ApplicationConfig;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.repository.IPlanetRepository;
import br.com.elo7.sonda.candidato.api.repository.IProbeRepository;
import br.com.elo7.sonda.candidato.api.service.impl.PlanetService;
import br.com.elo7.sonda.candidato.api.service.impl.ProbeService;
import br.com.elo7.sonda.candidato.domain.exceptions.type.BusinessException;
import br.com.elo7.sonda.candidato.domain.exceptions.type.ObjectNotFoundException;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.constants.Direction;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.PlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.ProbeEntity;
import br.com.elo7.sonda.candidato.fakes.PlanetEntityFake;
import br.com.elo7.sonda.candidato.fakes.PlanetFake;
import br.com.elo7.sonda.candidato.fakes.ProbeEntityFake;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Import(ApplicationConfig.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PlanetServiceTest {

    private IPlanetService service;
    private IProbeService probeService;

    @MockBean
    private IPlanetRepository repository;

    @MockBean
    private IProbeRepository probeRepository;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        this.probeService = new ProbeService(probeRepository, modelMapper);
        this.service = new PlanetService(repository, modelMapper, probeService);
    }

    @Test
    @DisplayName("Save planet successfully.")
    public void savePlanetSuccessfully() {
        PlanetEntity planetEntity = PlanetEntityFake.create();

        // Mock
        Planet planetSaved = PlanetFake.create();
        planetSaved.setId(1L);

        Mockito.when(repository.save(Mockito.any(Planet.class))).thenReturn(planetSaved);

        // Exec
        Planet newPlanet = this.service.insert(planetEntity);

        // Assert
        assertThat(newPlanet.getName()).isEqualTo(planetSaved.getName());
        assertThat(newPlanet.getId()).isEqualTo(planetSaved.getId());
        assertThat(newPlanet.getHeight()).isEqualTo(planetSaved.getHeight());
        assertThat(newPlanet.getWidth()).isEqualTo(planetSaved.getWidth());
        assertThat(newPlanet.getCreatedAt()).isNotNull();

    }

    @Test
    @DisplayName("Save duplicate planet.")
    public void saveDuplicatePlanet() {
        PlanetEntity planetEntity = PlanetEntityFake.create();

        // Mock
        Planet planetSaved = PlanetFake.create();
        planetSaved.setId(1L);

        Mockito.when(repository.save(Mockito.any(Planet.class))).thenReturn(planetSaved);
        Mockito.when(repository.existsPlanetByName(Mockito.anyString())).thenReturn(true);

        // Assert
        Assertions.assertThrows(
                BusinessException.class, () -> this.service.insert(planetEntity));

    }

    @Test
    @DisplayName("Get planet by id.")
    public void getPlanetById() {

        // Mock
        Planet planetSaved = PlanetFake.create();
        planetSaved.setId(1L);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(planetSaved));

        // Exec
        Planet planet = this.service.getById(1L);

        // Assert
        assertThat(planet.getName()).isEqualTo(planetSaved.getName());
        assertThat(planet.getId()).isEqualTo(planetSaved.getId());
        assertThat(planet.getHeight()).isEqualTo(planetSaved.getHeight());
        assertThat(planet.getWidth()).isEqualTo(planetSaved.getWidth());
        assertThat(planet.getCreatedAt()).isNotNull();

    }

    @Test
    @DisplayName("Planet not found by id.")
    public void planetNotFoundById() {

        // Mock
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // Assert
        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> this.service.getById(1L));

    }

    @Test
    @DisplayName("Get a planet already deleted.")
    public void getPlanetAlreadyDeleted() {

        // Mock
        Planet planetSaved = PlanetFake.create();
        planetSaved.setId(1L);
        planetSaved.setDeletedAt(PlanetFake.getDeletedAt());

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(planetSaved));

        // Assert
        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> this.service.getById(1L));

    }

    @Test
    @DisplayName("Update planet successfully.")
    public void updatePlanetSuccessfully() {
        Planet planetUpdateData = PlanetFake.create();

        // Mock
        Planet planetDb = PlanetFake.create();
        planetDb.setId(1L);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(planetDb));
        Mockito.when(repository.save(Mockito.any(Planet.class))).thenReturn(planetDb);

        // Exec
        Planet planetUpdated = this.service.update(planetUpdateData, 1L);

        // Assert
        assertThat(planetUpdated.getName()).isEqualTo(planetDb.getName());
        assertThat(planetUpdated.getId()).isEqualTo(planetDb.getId());
        assertThat(planetUpdated.getHeight()).isEqualTo(planetDb.getHeight());
        assertThat(planetUpdated.getWidth()).isEqualTo(planetDb.getWidth());
        assertThat(planetUpdated.getCreatedAt()).isNotNull();
        assertThat(planetUpdated.getUpdatedAt()).isNotNull();

    }

    @Test
    @DisplayName("Update duplicate planet.")
    public void updateDuplicatePlanet() {
        Planet planetUpdateData = PlanetFake.create();

        // Mock
        Mockito.when(repository.existsPlanetByNameAndIdNot(Mockito.anyString(), Mockito.anyLong())).thenReturn(true);

        // Assert
        Assertions.assertThrows(
                BusinessException.class, () -> this.service.update(planetUpdateData, 1L));

    }

    @Test
    @DisplayName("Update unregistered planet.")
    public void updateUnregisteredPlanet() {
        Planet planetUpdateData = PlanetFake.create();

        // Mock
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        // Assert
        Assertions.assertThrows(
                ObjectNotFoundException.class, () -> this.service.update(planetUpdateData, 1L));

    }

    @Test
    @DisplayName("Update planet size.")
    public void updatePlanetSize() {
        Planet planetUpdateData = PlanetFake.create();

        // Mock
        Planet planetDb = PlanetFake.create();
        planetDb.setId(1L);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(planetDb));
        Mockito.when(repository.save(Mockito.any(Planet.class))).thenReturn(planetDb);

        // Exec
        Planet planetUpdated = this.service.updatePlanetSize(planetUpdateData, 1L);

        // Assert
        assertThat(planetUpdated.getName()).isEqualTo(planetDb.getName());
        assertThat(planetUpdated.getId()).isEqualTo(planetDb.getId());
        assertThat(planetUpdated.getHeight()).isEqualTo(planetDb.getHeight());
        assertThat(planetUpdated.getWidth()).isEqualTo(planetDb.getWidth());
        assertThat(planetUpdated.getCreatedAt()).isNotNull();
        assertThat(planetUpdated.getUpdatedAt()).isNotNull();

    }

    @Test
    @DisplayName("Add and move prove.")
    public void addAndMoveProve() {
        IPlanetEntity planetEntity = PlanetEntityFake.create();
        planetEntity.setWidth(5);
        planetEntity.setHeight(5);

        ProbeEntity probe1 = ProbeEntityFake.create();
        probe1.setX(1);
        probe1.setY(2);
        probe1.setDirection(Direction.N);
        probe1.setCommands("LMLMLMLMM");

        ProbeEntity probe2 = ProbeEntityFake.create();
        probe2.setX(3);
        probe2.setY(3);
        probe2.setDirection(Direction.E);
        probe2.setCommands("MMRMMRMRRML");

        List<IProbeEntity> probeList = new ArrayList<>();
        probeList.add(probe1);
        probeList.add(probe2);

        planetEntity.setProbes(probeList);

        // Mock
        Planet planetSaved = PlanetFake.create();
        planetSaved.setId(1L);

         Mockito.when(repository.save(Mockito.any(Planet.class))).thenReturn(planetSaved);
         Mockito.when(probeRepository.save(Mockito.any(Probe.class))).thenReturn(Mockito.any(Probe.class));

         // exec
        Planet planet = service.addProbePlanet(planetEntity);

        assertThat(planet).isNotNull();

    }


}
