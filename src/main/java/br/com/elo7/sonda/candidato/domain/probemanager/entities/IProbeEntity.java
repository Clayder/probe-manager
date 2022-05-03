package br.com.elo7.sonda.candidato.domain.probemanager.entities;

import java.util.List;

public interface IProbeEntity {
    String getCommands();

    void applyCommandToProbe(char command, IPlanetEntity planetEntity);

    void setX(int x);

}
