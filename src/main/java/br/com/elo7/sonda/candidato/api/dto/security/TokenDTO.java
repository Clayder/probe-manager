package br.com.elo7.sonda.candidato.api.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Planet.*;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Planet.PASSWORD_LENGTH_FIELD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    @NotEmpty(message = REQUIRED_FIELD)
    private String token;

    @NotEmpty(message = REQUIRED_FIELD)
    private String type;
}
