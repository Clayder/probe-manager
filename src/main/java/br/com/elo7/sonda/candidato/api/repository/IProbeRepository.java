package br.com.elo7.sonda.candidato.api.repository;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProbeRepository extends JpaRepository<Probe, Long> {
    Boolean existsProbeByPlanetAndXAndY(Planet planet, int x, int y);
}
