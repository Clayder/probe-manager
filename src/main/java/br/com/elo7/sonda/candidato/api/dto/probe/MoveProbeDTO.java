package br.com.elo7.sonda.candidato.api.dto.probe;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MoveProbeDTO {

    @NotEmpty(message = REQUIRED_FIELD)
    private String commands;
}
