package br.com.elo7.sonda.candidato.api.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotEmpty;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Planet.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotEmpty(message = REQUIRED_FIELD)
    @Length(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = NAME_LENGTH_FIELD)
    private String email;

    @NotEmpty(message = REQUIRED_FIELD)
    @Length(min = PASSWORD_SIZE_MIN, max = PASSWORD_SIZE_MAX, message = PASSWORD_LENGTH_FIELD)
    private String password;

    public UsernamePasswordAuthenticationToken convert() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
