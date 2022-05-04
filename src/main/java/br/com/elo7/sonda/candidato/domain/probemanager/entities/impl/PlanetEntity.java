package br.com.elo7.sonda.candidato.domain.probemanager.entities.impl;

import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PlanetEntity implements IPlanetEntity {
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int width;

    @NonNull
    private int height;

    @NonNull
    List<IProbeEntity> probes;

    public PlanetEntity(Long id, String name, int width, int height) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
    }
}
