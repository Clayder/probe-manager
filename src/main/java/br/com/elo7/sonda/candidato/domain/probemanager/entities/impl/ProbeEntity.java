package br.com.elo7.sonda.candidato.domain.probemanager.entities.impl;

import br.com.elo7.sonda.candidato.domain.exceptions.messages.ErrorMessage;
import br.com.elo7.sonda.candidato.domain.exceptions.type.BusinessException;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.constants.Command;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.constants.Direction;
import lombok.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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

    private static Logger logger = LoggerFactory.getLogger(ProbeEntity.class);

    @Builder
    public ProbeEntity(Long id, int x, int y, char direction, String commands) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.commands = commands;
    }

    @Override
    public void applyCommandToProbe(char command, IPlanetEntity planetEntity) {
        switch (command) {
            case Command.R -> turnProbeRight();
            case Command.L -> turnProbeLeft();
            case Command.M -> moveProbeForward(planetEntity);
            default -> throw new BusinessException(
                    ErrorMessage.INVALID_COMMAND + ": " + command, logger
            );
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
            default -> throw new BusinessException(
                    ErrorMessage.INVALID_DIRECTION + ": " + this.getDirection(), logger
            );
        }

        if ((newX > planetEntity.getWidth() || newX < 0) || (newY > planetEntity.getHeight() || newY < 0)) {

            String msgError = ErrorMessage.PROBE_CANNOT_LEAVE_PLANET +
                    " newX: %s ".formatted(newX) +
                    "newY: %s".formatted(newY);
            throw new BusinessException(msgError, logger);
        }

        logger.debug(String.format("Performing the probe displacement: newX: %d newY: %d", newX, newY));
        this.setX(newX);
        this.setY(newY);
    }

    private void turnProbeLeft() {
        char newDirection = switch (this.getDirection()) {
            case Direction.N -> Direction.W;
            case Direction.W -> Direction.S;
            case Direction.S -> Direction.E;
            case Direction.E -> Direction.N;
            default -> throw new BusinessException(
                    ErrorMessage.INVALID_DIRECTION + ": " + this.getDirection(), logger
            );
        };
        logger.debug(String.format("Moving to direction: %c", newDirection));
        this.setDirection(newDirection);
    }

    private void turnProbeRight() {
        char newDirection = switch (this.getDirection()) {
            case Direction.N -> Direction.E;
            case Direction.E -> Direction.S;
            case Direction.S -> Direction.W;
            case Direction.W -> Direction.N;
            default -> throw new BusinessException(
                    ErrorMessage.INVALID_DIRECTION + ": " + this.getDirection(), logger
            );
        };
        logger.debug(String.format("Moving to direction: %c", newDirection));
        this.setDirection(newDirection);

    }
}
