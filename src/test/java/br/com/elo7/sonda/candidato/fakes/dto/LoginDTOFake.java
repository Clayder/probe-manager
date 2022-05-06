package br.com.elo7.sonda.candidato.fakes.dto;

import br.com.elo7.sonda.candidato.api.dto.security.LoginDTO;

public abstract class LoginDTOFake {

    public static LoginDTO create() {
        LoginDTO dto = new LoginDTO();
        dto.setEmail("admin@gmail.com");
        dto.setPassword("123456");
        return dto;
    }

}
