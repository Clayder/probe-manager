package br.com.elo7.sonda.candidato.api.dto.planet;

import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDTO extends PlanetSchemaDTO {

    @NotEmpty(message = REQUIRED_FIELD)
    private List<ProbeDTO> probes;

}
