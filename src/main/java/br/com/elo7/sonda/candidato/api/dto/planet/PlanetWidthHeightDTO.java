package br.com.elo7.sonda.candidato.api.dto.planet;

import br.com.elo7.sonda.candidato.core.dto.AbstractCoreDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.GREATER_THAN_ZERO;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetWidthHeightDTO extends AbstractCoreDto {

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private int width;

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private int height;
}
