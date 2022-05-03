package br.com.elo7.sonda.candidato.domain.probemanager.entities;

import br.com.elo7.sonda.candidato.domain.exceptions.messages.ErrorMessage;
import br.com.elo7.sonda.candidato.domain.exceptions.type.BusinessException;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.constants.Command;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.constants.Direction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ProbeEntity implements IProbeEntity {

    private Long id;

	@NonNull
	private int x;

	@NonNull
	private int y;

	@NonNull
	private char direction;

	@NonNull
	private String commands;

	@Override
	public void applyCommandToProbe(char command, IPlanetEntity planetEntity) {
		switch (command) {
			case Command.R -> turnProbeRight();
			case Command.L -> turnProbeLeft();
			case Command.M -> moveProbeForward(planetEntity);
		}
	}

	private void moveProbeForward(IPlanetEntity planetEntity) {
		int newX = this.getX();
		int newY = this.getY();
		switch (this.getDirection()) {
			case Direction.N -> newY++;
			case Direction.W -> newX--;
			case Direction.S -> newY--;
			case Direction.E -> newX++;
		}

		if ((newX > planetEntity.getWidth() || newX < 0) || (newY > planetEntity.getHeight() || newY < 0)) {
			throw new BusinessException(ErrorMessage.PROBE_CANNOT_LEAVE_PLANET +
					" newX: %s ".formatted(newX) +
					"newY: %s".formatted(newY));
		}
		this.setX(newX);
		this.setY(newY);
	}

	private void turnProbeLeft() {
		char newDirection = switch (this.getDirection()) {
			case Direction.N -> Direction.W;
			case Direction.W -> Direction.S;
			case Direction.S -> Direction.E;
			default -> Direction.N;
		};
		this.setDirection(newDirection);
	}

	private void turnProbeRight() {
		char newDirection = switch (this.getDirection()) {
			case Direction.N -> Direction.E;
			case Direction.E -> Direction.S;
			case Direction.S -> Direction.W;
			default -> Direction.N;
		};
		System.out.println(newDirection);
		this.setDirection(newDirection);

	}
}
