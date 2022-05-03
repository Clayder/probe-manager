package br.com.elo7.sonda.candidato.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDTO {
	private int width;
	private int height;
	private List<ProbeDTO> probes;

}
