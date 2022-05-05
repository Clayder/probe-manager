package br.com.elo7.sonda.candidato.fakes.dto;

import br.com.elo7.sonda.candidato.api.dto.planet.PlanetDTO;
import br.com.elo7.sonda.candidato.api.dto.planet.PlanetSchemaDTO;

public abstract class PlanetDTOFake {

    public static final String NAME_PLANETA_1 = "Planeta1";
    public static final int HEIGHT1 = 5;
    public static final int WIDTH1 = 5;

    public static PlanetDTO create() {

        PlanetDTO planetDTO = new PlanetDTO();
        planetDTO.setName(NAME_PLANETA_1);
        planetDTO.setHeight(HEIGHT1);
        planetDTO.setWidth(WIDTH1);
        planetDTO.setProbes(ProbeDTOFake.createList());

        return planetDTO;
    }

    public static PlanetDTO createWithoutProbe() {

        PlanetDTO planetDTO = new PlanetDTO();
        planetDTO.setName(NAME_PLANETA_1);
        planetDTO.setHeight(HEIGHT1);
        planetDTO.setWidth(WIDTH1);

        return planetDTO;
    }
}
