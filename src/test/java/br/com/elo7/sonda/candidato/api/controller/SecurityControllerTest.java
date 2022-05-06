package br.com.elo7.sonda.candidato.api.controller;

import br.com.elo7.sonda.candidato.api.core.controller.CoreControllerTes;
import br.com.elo7.sonda.candidato.fakes.dto.LoginDTOFake;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.Controller.Security.PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SecurityControllerTest extends CoreControllerTes {

    @Autowired
    private MockMvc mvc;

    private String path;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        this.path = PATH;
    }

    @Test
    @DisplayName("Create token successfully.")
    public void createTokenSuccessfully() throws Exception {

        // exec
        mvc
                .perform(MockMvcRequestBuilders
                        .post(this.path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(LoginDTOFake.create())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("type").value("Bearer"))
                .andExpect(jsonPath("token").isNotEmpty());


    }


}
