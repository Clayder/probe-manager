package br.com.elo7.sonda.candidato.fakes;

import br.com.elo7.sonda.candidato.api.model.Planet;

import java.sql.Timestamp;

public abstract class PlanetFake {

    private static final Timestamp CREATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp UPDATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp DELETED_AT = null;
    public static final String NAME = "PlanetA";
    public static final int HEIGHT = 5;
    public static final int WIDTH = 5;


    public static Planet create() {
        return Planet.builder()
                .name(NAME)
                .height(HEIGHT)
                .width(WIDTH)
                .probes(null)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .deletedAt(DELETED_AT)
                .build();

    }
}
