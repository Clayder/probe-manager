package br.com.elo7.sonda.candidato.api.dto.probe;

import br.com.elo7.sonda.candidato.core.dto.AbstractCoreDto;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.GREATER_THAN_ZERO;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProbeDTO extends AbstractCoreDto {

	@NotNull(message=REQUIRED_FIELD)
	@Min(value = 0, message = GREATER_THAN_ZERO)
	private int x;

	@NotNull(message=REQUIRED_FIELD)
	@Min(value = 0, message = GREATER_THAN_ZERO)
	private int y;

	@NotEmpty(message= REQUIRED_FIELD)
	private char direction;

	@NotEmpty(message= REQUIRED_FIELD)
	private String commands;
}
