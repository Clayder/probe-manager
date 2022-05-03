package br.com.elo7.sonda.candidato.domain.probemanager.entities;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class PlanetEntity implements IPlanetEntity{
    private int id;

	@NonNull
	private int width;

	@NonNull
	private int height;

	@NonNull
	List<IProbeEntity> probes;
}
