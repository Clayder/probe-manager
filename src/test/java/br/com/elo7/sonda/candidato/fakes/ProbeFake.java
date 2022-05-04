package br.com.elo7.sonda.candidato.fakes;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.constants.Direction;

import java.sql.Timestamp;

public abstract class ProbeFake {

    private static final Timestamp CREATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp UPDATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp DELETED_AT = null;
    public static final int X = 1;
    public static final int Y = 3;


    public static Probe create(Planet planet) {
        return Probe.builder()
                .x(X)
                .y(Y)
                .direction(Direction.N)
                .planet(planet)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .deletedAt(DELETED_AT)
                .build();
    }
}
