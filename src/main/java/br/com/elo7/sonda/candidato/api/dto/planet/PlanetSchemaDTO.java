package br.com.elo7.sonda.candidato.api.dto.planet;

import br.com.elo7.sonda.candidato.core.dto.AbstractCoreDto;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Planet.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetSchemaDTO extends AbstractCoreDto {

    @NotEmpty(message = REQUIRED_FIELD)
    @Length(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = NAME_LENGTH_FIELD)
    private String name;

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private int width;

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private int height;

}
