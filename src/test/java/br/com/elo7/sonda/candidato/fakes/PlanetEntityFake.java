package br.com.elo7.sonda.candidato.fakes;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.PlanetEntity;

import java.sql.Timestamp;

public abstract class PlanetEntityFake {

    private static final Timestamp CREATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp UPDATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp DELETED_AT = null;
    public static final String NAME = "PlanetA";
    public static final int HEIGHT = 5;
    public static final int WIDTH = 5;


    public static PlanetEntity create() {
        Planet planet = PlanetFake.create();
        return PlanetEntity.builder()
                .name(planet.getName())
                .height(planet.getHeight())
                .width(planet.getWidth())
                .probes(null)
                .build();

    }
}
