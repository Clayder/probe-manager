package br.com.elo7.sonda.candidato.fakes.model;

import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.constants.Direction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public abstract class ProbeFake {

    private static final Timestamp CREATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp UPDATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp DELETED_AT = null;

    public static final int X1 = 1;
    public static final int Y1 = 2;
    public static final String COMMANDS1 = "LMLMLMLMM";

    public static final int X2 = 3;
    public static final int Y2 = 3;
    public static final String COMMANDS2 = "MMRMMRMRRML";

    public static Probe create(Planet planet) {
        return Probe.builder()
                .x(X1)
                .y(Y1)
                .direction(Direction.N)
                .planet(planet)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .deletedAt(DELETED_AT)
                .build();
    }

    public static Probe create2(Planet planet) {
        return Probe.builder()
                .x(X2)
                .y(Y2)
                .direction(Direction.E)
                .planet(planet)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .deletedAt(DELETED_AT)
                .build();
    }

    public static List<Probe> createList(Planet planet) {
        List<Probe> list = new ArrayList<>();
        list.add(create(planet));
        list.add(create2(planet));
        return list;
    }


    public static Probe createWithoutPlanet() {
        return Probe.builder()
                .x(X1)
                .y(Y1)
                .direction(Direction.N)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .deletedAt(DELETED_AT)
                .build();
    }
}
