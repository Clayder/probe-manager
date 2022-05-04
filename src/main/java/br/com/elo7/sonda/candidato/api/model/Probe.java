package br.com.elo7.sonda.candidato.api.model;

import br.com.elo7.sonda.candidato.core.model.AbstractCoreModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.GREATER_THAN_ZERO;
import static br.com.elo7.sonda.candidato.api.constants.IConstants.MessageError.Default.REQUIRED_FIELD;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Probe extends AbstractCoreModel {

    @NotNull(message=REQUIRED_FIELD)
	@Min(value = 0, message = GREATER_THAN_ZERO)
    private Integer x;

    @NotNull(message=REQUIRED_FIELD)
	@Min(value = 0, message = GREATER_THAN_ZERO)
    private Integer y;

    @NotEmpty(message= REQUIRED_FIELD)
    private char direction;

    @ManyToOne
	@JoinColumn(name="planet_id")
	private Planet planet;

}
