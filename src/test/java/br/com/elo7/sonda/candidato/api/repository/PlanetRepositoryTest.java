package br.com.elo7.sonda.candidato.api.repository;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.fakes.PlanetFake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PlanetRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IPlanetRepository planetRepository;

    @Test
    public void testExistsPlanetByName() {
        Planet planet = PlanetFake.create();
        entityManager.persist(planet);
        entityManager.getEntityManager().getTransaction().commit();


        boolean exists = planetRepository.existsPlanetByName(PlanetFake.NAME);

        assertThat(exists).isTrue();

    }

}
