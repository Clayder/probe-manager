package br.com.elo7.sonda.candidato.api.repository;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.fakes.PlanetFake;
import br.com.elo7.sonda.candidato.fakes.ProbeFake;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.RollbackException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProbeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IProbeRepository probeRepository;

    @Test
    @DisplayName("Create collision between probes.")
    public void testCollisionBetweenProbes() {

        /**
         * Create two probes at the same location on the planet
         */
        Planet planet = PlanetFake.create();
        entityManager.persist(planet);

        Probe probe1 = ProbeFake.create(planet);
        entityManager.persist(probe1);

        Probe probe2 = ProbeFake.create(planet);
        entityManager.persist(probe2);


        Assertions.assertThrows(
                RollbackException.class, () -> entityManager.getEntityManager().getTransaction().commit());

    }

    @Test
    @DisplayName("Check if there is collision between probes.")
    public void testCheckIfCollisionBetweenProbes() {

        Planet planet = PlanetFake.create();
        entityManager.persist(planet);

        Probe probe1 = ProbeFake.create(planet);
        entityManager.persist(probe1);

        entityManager.getEntityManager().getTransaction().commit();


        boolean isCollision = probeRepository.existsProbeByPlanetAndXAndY(planet, probe1.getX(), probe1.getY());

        assertThat(isCollision).isTrue();

    }


}
