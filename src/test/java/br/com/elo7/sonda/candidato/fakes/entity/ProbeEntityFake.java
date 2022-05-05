package br.com.elo7.sonda.candidato.fakes.entity;

import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.impl.ProbeEntity;
import br.com.elo7.sonda.candidato.fakes.model.ProbeFake;

import java.sql.Timestamp;

public abstract class ProbeEntityFake {

    private static final Timestamp CREATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp UPDATED_AT = new Timestamp(System.currentTimeMillis());
    private static final Timestamp DELETED_AT = null;
    public static final int X = 1;
    public static final int Y = 2;
    public static final String COMMANDS = "LMLMLMLMM";


    public static ProbeEntity create() {
        Probe probe = ProbeFake.createWithoutPlanet();
        return ProbeEntity.builder()
                .x(probe.getX())
                .y(probe.getY())
                .direction(probe.getDirection())
                .commands(COMMANDS)
                .build();
    }
}
