package br.com.elo7.sonda.candidato.api.dto.planet;

import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDTO extends PlanetSchemaDTO {
    private List<ProbeDTO> probes;

}
