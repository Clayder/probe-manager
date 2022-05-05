package br.com.elo7.sonda.candidato.api.controller;


import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Planet.PATH;

import br.com.elo7.sonda.candidato.api.constants.IConstants;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.fakes.dto.PlanetDTOFake;
import br.com.elo7.sonda.candidato.fakes.dto.ProbeDTOFake;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mvc;

    private String path;

    @BeforeEach
    public void setUp() throws Exception {
        this.path = PATH;
    }

    @Test
    @DisplayName("Create planet with probes successfully.")
    public void createPlanetWithProbesSuccessfully() throws Exception {

        PlanetDTO planetDTO = PlanetDTOFake.create();
        String json = new ObjectMapper().writeValueAsString(planetDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(this.path.concat(IConstants.Controller.Probe.NAME))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name").value(planetDTO.getName()))
            .andExpect(jsonPath("width").value(planetDTO.getWidth()))
            .andExpect(jsonPath("height").value(planetDTO.getHeight()))
            .andExpect(jsonPath("$.probes[0].x").value(1))
            .andExpect(jsonPath("$.probes[0].y").value(3))
            .andExpect(jsonPath("$.probes[1].x").value(5))
            .andExpect(jsonPath("$.probes[1].y").value(1));

    }

    @Test
    @DisplayName("Error create planet with probes.")
    public void errorCreatePlanetWithProbes() throws Exception {

        PlanetDTO planetDTO = PlanetDTOFake.create();
        planetDTO.setName("");
        planetDTO.setHeight(-1);
        planetDTO.setWidth(-1);
        planetDTO.setProbes(null);
        String json = new ObjectMapper().writeValueAsString(planetDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(this.path.concat(IConstants.Controller.Probe.NAME))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Create planet successfully.")
    public void createPlanetSuccessfully() throws Exception {

        PlanetDTO planetDTO = PlanetDTOFake.createWithoutProbe();
        String json = new ObjectMapper().writeValueAsString(planetDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("name").value(planetDTO.getName()))
            .andExpect(jsonPath("width").value(planetDTO.getWidth()))
            .andExpect(jsonPath("height").value(planetDTO.getHeight()));

    }

    @Test
    @DisplayName("Error create planet successfully.")
    public void errorCreatePlanetSuccessfully() throws Exception {

        PlanetDTO planetDTO = PlanetDTOFake.createWithoutProbe();
        planetDTO.setName("");
        planetDTO.setHeight(-1);
        planetDTO.setWidth(-1);

        String json = new ObjectMapper().writeValueAsString(planetDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Delete planet successfully.")
    public void deletePlanetSuccessfully() throws Exception {

        // Create new Planet
        PlanetDTO planetDTO = PlanetDTOFake.createWithoutProbe();
        planetDTO.setName("PlanetInit");
        String json = new ObjectMapper().writeValueAsString(planetDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isCreated());

        // Exec
        mvc
            .perform(MockMvcRequestBuilders
                .delete(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Assert
        mvc
            .perform(MockMvcRequestBuilders
                .get(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete planet not found.")
    public void deletePlanetNotFound() throws Exception {

        // Exec
        mvc
            .perform(MockMvcRequestBuilders
                .delete(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Update size planet successfully.")
    public void updateSizePlanetSuccessfully() throws Exception {

        // Create new Planet
        PlanetDTO oldPlanetDTO = PlanetDTOFake.createWithoutProbe();
        oldPlanetDTO.setName("PlanetInit");
        String json = new ObjectMapper().writeValueAsString(oldPlanetDTO);

        mvc
            .perform(MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated());

        // Exec
        PlanetDTO planetUpdateDTO = PlanetDTOFake.create();
        planetUpdateDTO.setHeight(15);
        planetUpdateDTO.setWidth(15);

        String jsonUpdate = new ObjectMapper().writeValueAsString(planetUpdateDTO);

        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .patch(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonUpdate);

        mvc
            .perform(requestUpdate)
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(oldPlanetDTO.getName()))
            .andExpect(jsonPath("width").value(planetUpdateDTO.getWidth()))
            .andExpect(jsonPath("height").value(planetUpdateDTO.getHeight()));
    }

    @Test
    @DisplayName("Error update size planet.")
    public void errorUpdateSizePlanetSuccessfully() throws Exception {

       // Create new Planet
        PlanetDTO oldPlanetDTO = PlanetDTOFake.createWithoutProbe();
        oldPlanetDTO.setName("PlanetInit");
        String json = new ObjectMapper().writeValueAsString(oldPlanetDTO);

        mvc
            .perform(MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated());

        // Exec
        PlanetDTO planetUpdateDTO = PlanetDTOFake.create();
        planetUpdateDTO.setHeight(-1);
        planetUpdateDTO.setWidth(-1);

        String jsonUpdate = new ObjectMapper().writeValueAsString(planetUpdateDTO);

        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .patch(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonUpdate);

        mvc
            .perform(requestUpdate)
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get planet by ID.")
    public void getPlanetById() throws Exception {

       // Create new Planet
        PlanetDTO oldPlanetDTO = PlanetDTOFake.createWithoutProbe();
        oldPlanetDTO.setName("PlanetInit");
        String json = new ObjectMapper().writeValueAsString(oldPlanetDTO);

        mvc
            .perform(MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated());

        // Exec
        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .get(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
            .perform(requestUpdate)
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(oldPlanetDTO.getName()))
            .andExpect(jsonPath("width").value(oldPlanetDTO.getWidth()))
            .andExpect(jsonPath("height").value(oldPlanetDTO.getHeight()));;
    }

    @Test
    @DisplayName("Get planet not found.")
    public void getPlanetNotFound() throws Exception {

        // Exec
        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .get(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
            .perform(requestUpdate)
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("List planets.")
    public void listPlanets() throws Exception {

       // Create new Planet
        PlanetDTO oldPlanetDTO = PlanetDTOFake.createWithoutProbe();
        oldPlanetDTO.setName("PlanetInit");
        String json = new ObjectMapper().writeValueAsString(oldPlanetDTO);

        mvc
            .perform(MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated());

        // Exec
        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .get(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
            .perform(requestUpdate)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].id").value(1L))
            .andExpect(jsonPath("$.content[0].name").value(oldPlanetDTO.getName()))
            .andExpect(jsonPath("$.content[0].width").value(oldPlanetDTO.getWidth()))
            .andExpect(jsonPath("$.content[0].height").value(oldPlanetDTO.getHeight()))
            .andExpect(jsonPath("totalPages").value(1))
            .andExpect(jsonPath("totalElements").value(1));

    }

    @Test
    @DisplayName("Insert probe on a specific planet.")
    public void insertProbeSpecificPlanet() throws Exception {

        // Create new Planet
        PlanetDTO oldPlanetDTO = PlanetDTOFake.createWithoutProbe();
        oldPlanetDTO.setName("PlanetInit");

        mvc
            .perform(MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(oldPlanetDTO)))
            .andExpect(status().isCreated());

        //Exec
        ProbeDTO newProbeDTO = ProbeDTOFake.create1();
        String json = new ObjectMapper().writeValueAsString(newProbeDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(this.path.concat("/"+1L+"/probes"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(oldPlanetDTO.getName()))
            .andExpect(jsonPath("width").value(oldPlanetDTO.getWidth()))
            .andExpect(jsonPath("height").value(oldPlanetDTO.getHeight()))
            .andExpect(jsonPath("$.probes[0].x").value(1))
            .andExpect(jsonPath("$.probes[0].y").value(3));

    }

    @Test
    @DisplayName("Error insert probe on a specific planet.")
    public void errorInsertProbeSpecificPlanet() throws Exception {

        // Create new Planet
        PlanetDTO oldPlanetDTO = PlanetDTOFake.createWithoutProbe();
        oldPlanetDTO.setName("PlanetInit");

        mvc
            .perform(MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(oldPlanetDTO)))
            .andExpect(status().isCreated());

        //Exec
        ProbeDTO newProbeDTO = ProbeDTOFake.create1();
        newProbeDTO.setX(-1);
        newProbeDTO.setY(-1);
        newProbeDTO.setCommands("");

        String json = new ObjectMapper().writeValueAsString(newProbeDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(this.path.concat("/"+1L+"/probes"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Update planet successfully.")
    public void updatePlanetSuccessfully() throws Exception {

        // Create new Planet
        PlanetDTO oldPlanetDTO = PlanetDTOFake.createWithoutProbe();
        oldPlanetDTO.setName("PlanetInit");
        String json = new ObjectMapper().writeValueAsString(oldPlanetDTO);

        mvc
            .perform(MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated());

        // Exec
        PlanetDTO planetUpdateDTO = PlanetDTOFake.create();
        planetUpdateDTO.setHeight(15);
        planetUpdateDTO.setWidth(15);

        String jsonUpdate = new ObjectMapper().writeValueAsString(planetUpdateDTO);

        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .put(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonUpdate);

        mvc
            .perform(requestUpdate)
            .andExpect(status().isOk())
            .andExpect(jsonPath("name").value(planetUpdateDTO.getName()))
            .andExpect(jsonPath("width").value(planetUpdateDTO.getWidth()))
            .andExpect(jsonPath("height").value(planetUpdateDTO.getHeight()))
            .andExpect(jsonPath("updatedAt").isNotEmpty());
    }

    @Test
    @DisplayName("Error update planet.")
    public void errorUpdatePlanet() throws Exception {

        // Create new Planet
        PlanetDTO oldPlanetDTO = PlanetDTOFake.createWithoutProbe();
        oldPlanetDTO.setName("PlanetInit");
        String json = new ObjectMapper().writeValueAsString(oldPlanetDTO);

        mvc
            .perform(MockMvcRequestBuilders
                .post(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
            .andExpect(status().isCreated());

        // Exec
        PlanetDTO planetUpdateDTO = PlanetDTOFake.create();
        planetUpdateDTO.setName("");
        planetUpdateDTO.setHeight(-1);
        planetUpdateDTO.setWidth(-3);

        String jsonUpdate = new ObjectMapper().writeValueAsString(planetUpdateDTO);

        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .put(this.path.concat("/" + 1L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonUpdate);

        mvc
            .perform(requestUpdate)
            .andExpect(status().isBadRequest());
    }


}
