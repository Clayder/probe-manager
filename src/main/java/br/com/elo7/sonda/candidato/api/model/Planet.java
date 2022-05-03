package br.com.elo7.sonda.candidato.api.model;

import br.com.elo7.sonda.candidato.core.model.AbstractCoreModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table
public class Planet extends AbstractCoreModel {

    private String name;
    private Integer width;
    private Integer height;

    @JsonBackReference
	@OneToMany(mappedBy = "planet")
	private List<Probe> probes = new ArrayList<>();

    @Builder
    public Planet(Long id, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, String name, Integer width, Integer height, List<Probe> probes) {
        super(id, createdAt, updatedAt, deletedAt);
        this.name = name;
        this.width = width;
        this.height = height;
        this.probes = probes;
    }
}
