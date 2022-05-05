package br.com.elo7.sonda.candidato.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Timestamp createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Timestamp updatedAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Timestamp deletedAt;

}
