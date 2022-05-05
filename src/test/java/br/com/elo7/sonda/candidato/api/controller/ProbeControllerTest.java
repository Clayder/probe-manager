package br.com.elo7.sonda.candidato.api.controller;


import br.com.elo7.sonda.candidato.api.dto.planet.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.MoveProbeDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.fakes.dto.MoveProbeDTOFake;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.*;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Probe.PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProbeControllerTest {

    @Autowired
    private MockMvc mvc;

    private String path;

    private Long probe1Id = 2L;


    @BeforeEach
    public void setUp() throws Exception {
        this.path = PATH;

        // Create planet with probes
        PlanetDTO planetDTO = PlanetDTOFake.create();
        String json = new ObjectMapper().writeValueAsString(planetDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(Controller.Planet.PATH.concat(Controller.Probe.NAME))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
            .perform(request)
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.probes[0].id").value(probe1Id))
            .andExpect(jsonPath("$.probes[1].id").value(3));
    }

    @Test
    @DisplayName("Move specific probe successfully.")
    public void moveSpecificProbeSuccessfully() throws Exception {

        // Exec
        MoveProbeDTO move = MoveProbeDTOFake.create();

        String jsonUpdate = new ObjectMapper().writeValueAsString(move);

        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .put(this.path.concat("/" + probe1Id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonUpdate);

        ResultActions perform = mvc
                .perform(requestUpdate);
        perform
            .andExpect(status().isOk())
            .andExpect(jsonPath("x").value(1))
            .andExpect(jsonPath("y").value(4));
    }

    @Test
    @DisplayName("Error move specific probe.")
    public void errorMoveSpecificProbe() throws Exception {

        // Exec
        MoveProbeDTO move = MoveProbeDTOFake.create();
        move.setCommands("");

        String jsonUpdate = new ObjectMapper().writeValueAsString(move);

        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .put(this.path.concat("/" + probe1Id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonUpdate);

        ResultActions perform = mvc
                .perform(requestUpdate);
        perform
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Delete probe successfully.")
    public void deleteProbeSuccessfully() throws Exception {

        // Exec
        mvc
            .perform(MockMvcRequestBuilders
                .delete(this.path.concat("/" + probe1Id))
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
    @DisplayName("Delete probe not found.")
    public void deleteProbeNotFound() throws Exception {

        // Exec
        mvc
            .perform(MockMvcRequestBuilders
                .delete(this.path.concat("/" + 10L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Get probe by ID.")
    public void getProbeById() throws Exception {

        // Exec
        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .get(this.path.concat("/" + probe1Id))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ProbeDTO probeDTO = ProbeDTOFake.create1();
        mvc
            .perform(requestUpdate)
            .andExpect(status().isOk())
            .andExpect(jsonPath("x").value(1))
            .andExpect(jsonPath("y").value(3));
    }

    @Test
    @DisplayName("Get probe not found.")
    public void getProbeNotFound() throws Exception {

        // Exec
        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .get(this.path.concat("/" + 10L))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
            .perform(requestUpdate)
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("List probes.")
    public void listPlanets() throws Exception {

        // Exec
        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .get(this.path)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestUpdate)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(probe1Id))
                .andExpect(jsonPath("$.content[0].x").value(1))
                .andExpect(jsonPath("$.content[0].y").value(3))
                .andExpect(jsonPath("totalPages").value(1))
                .andExpect(jsonPath("totalElements").value(2));
    }




}
