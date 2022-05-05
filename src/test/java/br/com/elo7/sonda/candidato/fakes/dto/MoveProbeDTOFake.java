package br.com.elo7.sonda.candidato.fakes.dto;

import br.com.elo7.sonda.candidato.api.dto.probe.MoveProbeDTO;

public abstract class MoveProbeDTOFake {

    public static MoveProbeDTO create() {
        MoveProbeDTO moveProbeDTO = new MoveProbeDTO();
        moveProbeDTO.setCommands("M");
        return moveProbeDTO;
    }
}
