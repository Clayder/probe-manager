package br.com.elo7.sonda.candidato.api.dto.probe;

import br.com.elo7.sonda.candidato.core.dto.AbstractCoreDto;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.GREATER_THAN_ZERO;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProbeDTO extends AbstractCoreDto {

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private int x;

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private int y;

    @NotNull(message = REQUIRED_FIELD)
    private char direction;

    @NotEmpty(message = REQUIRED_FIELD)
    private String commands;

    @Builder
    public ProbeDTO(Long id, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, int x, int y, char direction, String commands) {
        super(id, createdAt, updatedAt, deletedAt);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.commands = commands;
    }
}
