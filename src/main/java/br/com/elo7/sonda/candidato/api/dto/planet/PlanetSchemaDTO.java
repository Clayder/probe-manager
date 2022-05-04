package br.com.elo7.sonda.candidato.api.dto.planet;

import br.com.elo7.sonda.candidato.core.dto.AbstractCoreDto;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetSchemaDTO extends AbstractCoreDto {

	private String name;
	private int width;
	private int height;

}
