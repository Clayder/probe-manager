package br.com.elo7.sonda.candidato.api.model;

import br.com.elo7.sonda.candidato.core.model.AbstractCoreModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table
public class Probe extends AbstractCoreModel {

    private Integer x;
    private Integer y;
    private char direction;

    @ManyToOne
	@JoinColumn(name="planet_id")
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
