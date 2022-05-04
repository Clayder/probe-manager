package br.com.elo7.sonda.candidato.api.dto.planet;

import br.com.elo7.sonda.candidato.core.dto.AbstractCoreDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetWidthHeightDTO extends AbstractCoreDto {
    private int width;
	private int height;
}
