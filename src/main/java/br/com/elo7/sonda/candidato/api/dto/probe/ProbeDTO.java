package br.com.elo7.sonda.candidato.api.dto.probe;

import br.com.elo7.sonda.candidato.core.dto.AbstractCoreDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProbeDTO extends AbstractCoreDto {

	private int x;
	private int y;
	private char direction;
	private String commands;
}
