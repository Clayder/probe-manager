package br.com.elo7.sonda.candidato.core.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractCoreDto {

    private Long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

}
