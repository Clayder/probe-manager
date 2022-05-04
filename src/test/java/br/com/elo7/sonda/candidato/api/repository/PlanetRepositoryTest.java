package br.com.elo7.sonda.candidato.api.repository;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.fakes.PlanetFake;
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
public class PlanetRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IPlanetRepository planetRepository;

    @Test
    @DisplayName("Exists planet by name.")
    public void testExistsPlanetByName() {
        Planet planet = PlanetFake.create();
        entityManager.persist(planet);
        entityManager.getEntityManager().getTransaction().commit();

        boolean exists = planetRepository.existsPlanetByName(PlanetFake.NAME);

        assertThat(exists).isTrue();

    }

    @Test
    @DisplayName("Check if there is a planet by name and id.")
    public void testExistsPlanetByNameAndId() {
        Planet planet = PlanetFake.create();
        entityManager.persist(planet);
        entityManager.getEntityManager().getTransaction().commit();

        boolean notExists = planetRepository.existsPlanetByNameAndIdNot(PlanetFake.NAME, 100L);
        assertThat(notExists).isTrue();

        boolean exists = planetRepository.existsPlanetByNameAndIdNot(PlanetFake.NAME, 1L);
        assertThat(exists).isFalse();

    }

    @Test
    @DisplayName("Error saving planets with repeated names.")
    public void errorSavingPlanetsWithRepeatedNames() {

        /**
         * Create two planets with the same names
         */
        Planet planet = PlanetFake.create();
        Planet planet2 = PlanetFake.create();
        entityManager.persist(planet);
        entityManager.persist(planet2);

        Assertions.assertThrows(
                RollbackException.class, () -> entityManager.getEntityManager().getTransaction().commit());

    }

    @Test
    @DisplayName("Error updating planet to an existing name.")
    public void errorUpdatingPlanetToAnExistingName() {

        /**
         * Create two planets with different names
         */
        Planet planet1 = PlanetFake.create();
        entityManager.persist(planet1);

        Planet planet2 = PlanetFake.create();
        planet2.setName("Planet2");
        entityManager.persist(planet2);

        /**
         * Update planet1 name to planet2 name
         */
        Planet planetOld = planetRepository.getById(planet1.getId());
        planetOld.setName(planet2.getName());
        entityManager.persist(planetOld);

        Assertions.assertThrows(
                RollbackException.class, () -> entityManager.getEntityManager().getTransaction().commit());

    }

}
