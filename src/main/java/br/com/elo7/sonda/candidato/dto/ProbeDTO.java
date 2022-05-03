package br.com.elo7.sonda.candidato.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProbeDTO {
	private int x; 
	private int y;
	private char direction;
	private String commands;
}
