package br.com.elo7.sonda.candidato.domain.exceptions.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StandardError  {

    private Integer status;
    private String msg;
    private Long timeStamp;

}
