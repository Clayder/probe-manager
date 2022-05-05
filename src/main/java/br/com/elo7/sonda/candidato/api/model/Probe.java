package br.com.elo7.sonda.candidato.api.model;

import br.com.elo7.sonda.candidato.core.model.AbstractCoreModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import java.sql.Timestamp;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.GREATER_THAN_ZERO;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "x", "y", "planet_id" }) })
public class Probe extends AbstractCoreModel {

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private Integer x;

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private Integer y;

    @NotNull(message = REQUIRED_FIELD)
    private char direction;

    @ManyToOne()
    @JoinColumn(name = "planet_id")
    private Planet planet;

    @Builder
    public Probe(Long id, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, Integer x, Integer y, char direction, Planet planet) {
        super(id, createdAt, updatedAt, deletedAt);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.planet = planet;
    }
}
