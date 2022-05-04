package br.com.elo7.sonda.candidato.api.dto;

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
	private Long id;
	private String name;
	private int width;
	private int height;
	private List<ProbeDTO> probes;

}
