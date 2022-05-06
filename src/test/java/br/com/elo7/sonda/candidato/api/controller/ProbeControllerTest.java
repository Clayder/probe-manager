package br.com.elo7.sonda.candidato.api.controller;


import br.com.elo7.sonda.candidato.api.core.controller.CoreControllerTes;
import br.com.elo7.sonda.candidato.api.dto.probe.MoveProbeDTO;
import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.fakes.dto.MoveProbeDTOFake;
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

import java.util.List;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Probe.PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProbeControllerTest extends CoreControllerTes {

    @Autowired
    private MockMvc mvc;

    private String path;

    private Long probe1Id = 2L;


    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        this.path = PATH;

        // Create planet with probes
        List<Probe> probes = this.createPlanetWithProbes();
        this.probe1Id = probes.get(0).getId();
    }

    @Test
    @DisplayName("Move specific probe successfully.")
    public void moveSpecificProbeSuccessfully() throws Exception {

        // Exec
        MoveProbeDTO move = MoveProbeDTOFake.create();

        String jsonUpdate = new ObjectMapper().writeValueAsString(move);

        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .put(this.path.concat("/" + probe1Id))
                .header("Authorization", this.token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonUpdate);

        ResultActions perform = mvc
                .perform(requestUpdate);
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("x").value(1))
                .andExpect(jsonPath("y").value(3));
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
                .header("Authorization", this.token)
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
                        .header("Authorization", this.token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Assert
        mvc
                .perform(MockMvcRequestBuilders
                        .get(this.path.concat("/" + 1L))
                        .header("Authorization", this.token)
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
                        .header("Authorization", this.token)
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
                .header("Authorization", this.token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        ProbeDTO probeDTO = ProbeDTOFake.create1();
        mvc
                .perform(requestUpdate)
                .andExpect(status().isOk())
                .andExpect(jsonPath("x").value(1))
                .andExpect(jsonPath("y").value(2));
    }

    @Test
    @DisplayName("Get probe not found.")
    public void getProbeNotFound() throws Exception {

        // Exec
        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .get(this.path.concat("/" + 10L))
                .header("Authorization", this.token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestUpdate)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("List probes.")
    public void listProbes() throws Exception {

        // Exec
        MockHttpServletRequestBuilder requestUpdate = MockMvcRequestBuilders
                .get(this.path.concat("?orderBy=x"))
                .header("Authorization", this.token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc
                .perform(requestUpdate)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(probe1Id))
                .andExpect(jsonPath("$.content[0].x").value(1))
                .andExpect(jsonPath("$.content[0].y").value(2));
    }


}
