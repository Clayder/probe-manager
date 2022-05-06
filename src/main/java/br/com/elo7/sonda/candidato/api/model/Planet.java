package br.com.elo7.sonda.candidato.api.model;

import br.com.elo7.sonda.candidato.core.model.AbstractCoreModel;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Planet.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    @NotEmpty(message = REQUIRED_FIELD)
    @Length(min = NAME_SIZE_MIN, max = NAME_SIZE_MAX, message = NAME_LENGTH_FIELD)
    @Column(unique=true)
    private String name;

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private Integer width;

    @NotNull(message = REQUIRED_FIELD)
    @Min(value = 0, message = GREATER_THAN_ZERO)
    private Integer height;

    @JsonBackReference
    @OneToMany(mappedBy = "planet")
    private List<Probe> probes = new ArrayList<>();



}
