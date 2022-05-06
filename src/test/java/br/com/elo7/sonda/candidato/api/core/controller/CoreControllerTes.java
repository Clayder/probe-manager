package br.com.elo7.sonda.candidato.api.core.controller;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.model.User;
import br.com.elo7.sonda.candidato.api.repository.IPlanetRepository;
import br.com.elo7.sonda.candidato.api.repository.IProbeRepository;
import br.com.elo7.sonda.candidato.api.repository.IUserRepository;
import br.com.elo7.sonda.candidato.fakes.model.PlanetFake;
import br.com.elo7.sonda.candidato.fakes.model.ProbeFake;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CoreControllerTes {

    @Autowired
    protected IUserRepository userRepository;

    @Autowired
    protected IPlanetRepository planetRepository;

    @Autowired
    protected IProbeRepository probeRepository;

    protected String token;

    public void setUp() throws Exception {
        User user = new User();
        user.setUserName("admin");
        user.setEmail("admin@gmail.com");
        user.setPassword("$2a$10$sDt2Qfh3mMMseUQkRw0fBeMfNTq2Pr8Zsgp353t9H0RPlDBsCs3LG");
        userRepository.save(user);

        this.token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBUEkiLCJzdWIiOiIxIiwiaWF0IjoxNjUxODIxOTM5LCJleHAiOjE3MTQ4OTM5Mzl9.x_a9l7Y7f2WeRiLyZE-Xq0mRdF6TRbX8Ft5j6tqqwRo";
    }

    public Planet createPlanet() {
        return planetRepository.save(PlanetFake.create());
    }

    public List<Probe> createPlanetWithProbes() {
        Planet planet = this.createPlanet();
        return probeRepository.saveAll(ProbeFake.createList(planet));
    }
}
