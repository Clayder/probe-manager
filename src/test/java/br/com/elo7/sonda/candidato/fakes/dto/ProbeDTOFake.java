package br.com.elo7.sonda.candidato.fakes.dto;

import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.constants.Direction;

import java.util.ArrayList;
import java.util.List;

public abstract class ProbeDTOFake {

    public static final int X1 = 1;
    public static final int Y1 = 2;
    public static final String COMMANDS1 = "LMLMLMLMM";

    public static final int X2 = 3;
    public static final int Y2 = 3;
    public static final String COMMANDS2 = "MMRMMRMRRML";

    public static ProbeDTO create1() {
        return ProbeDTO.builder()
                .commands(COMMANDS1)
                .direction(Direction.N)
                .x(X1)
                .y(Y1)
                .build();
    }

    public static ProbeDTO create2() {
        return ProbeDTO.builder()
                .commands(COMMANDS2)
                .direction(Direction.E)
                .x(X2)
                .y(Y2)
                .build();
    }

    public static List<ProbeDTO> createList() {
        List<ProbeDTO> list = new ArrayList<>();
        list.add(create1());
        list.add(create2());
        return list;
    }

}
