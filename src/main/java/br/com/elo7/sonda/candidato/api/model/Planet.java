package br.com.elo7.sonda.candidato.api.model;

import br.com.elo7.sonda.candidato.core.model.AbstractCoreModel;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
@Table
public class Planet extends AbstractCoreModel {

    private String name;
    private Integer width;
    private Integer height;

    @Builder
    public Planet(Long id, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, String name, Integer width, Integer height) {
        super(id, createdAt, updatedAt, deletedAt);
        this.name = name;
        this.width = width;
        this.height = height;
    }

}
