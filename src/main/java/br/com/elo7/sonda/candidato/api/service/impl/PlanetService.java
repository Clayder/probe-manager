package br.com.elo7.sonda.candidato.api.service.impl;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.repository.IPlanetRepository;
import br.com.elo7.sonda.candidato.api.service.IPlanetService;
import br.com.elo7.sonda.candidato.api.service.IProbeService;
import br.com.elo7.sonda.candidato.domain.exceptions.messages.ErrorMessage;
import br.com.elo7.sonda.candidato.domain.exceptions.type.BusinessException;
import br.com.elo7.sonda.candidato.domain.exceptions.type.ObjectNotFoundException;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PlanetService implements IPlanetService {

    private IPlanetRepository planetRepository;
    private ModelMapper modelMapper;

    private IProbeService probeService;

    private static Logger logger = LoggerFactory.getLogger(PlanetService.class);

    public PlanetService(IPlanetRepository planetRepository, ModelMapper modelMapper, IProbeService probeService) {
        this.planetRepository = planetRepository;
        this.modelMapper = modelMapper;
        this.probeService = probeService;
    }

    /**
     * Add probe on planet
     */
    public Planet addProbePlanet(IPlanetEntity planetEntity) {
        Planet planetModel = this.insert(planetEntity);
        return addProbeByPlanet(planetEntity, planetModel);
    }

    @Override
    public Planet addProbeByPlanet(IPlanetEntity planetEntity, Planet planetModel) {
        List<Probe> Probes = this.probeService.convertAndMoveProbes(
                planetEntity, planetModel
        );
        planetModel.setProbes(Probes);
        return planetModel;
    }

    public Planet insert(IPlanetEntity planetEntity) {
        Planet planet = modelMapper.map(planetEntity, Planet.class);
        return insert(planet);
    }

    @Override
    public Planet insert(Planet planet) {
        existsPlanet(planet);
        planet.setId(null);
        planet.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return planetRepository.save(planet);
    }

    private void existsPlanet(Planet planet) {
        boolean existsPlanet = this.planetRepository.existsPlanetByName(planet.getName());

        if (existsPlanet) {
            throw new BusinessException(ErrorMessage.DUPLICATE_PLANET, logger);
        }
    }


    @Override
    public Planet getById(Long id) {
        Optional<Planet> obj = planetRepository.findById(id);
        Planet planet = obj.orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.PLANET_NOT_FOUND, logger));
        if (planet.getDeletedAt() != null) {
            throw new ObjectNotFoundException(ErrorMessage.PLANET_NOT_FOUND, logger);
        }
        return planet;
    }

    @Override
    public Planet update(Planet planet, Long id) {

        boolean existsPlanet = this.planetRepository.existsPlanetByNameAndIdNot(planet.getName(), id);

        if (existsPlanet) {
            throw new BusinessException(ErrorMessage.DUPLICATE_PLANET, logger);
        }

        Planet oldPlanet = this.getById(id);
        planet.setId(oldPlanet.getId());
        planet.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        planet.setCreatedAt(oldPlanet.getCreatedAt());
        planet.setDeletedAt(oldPlanet.getDeletedAt());
        return planetRepository.save(planet);
    }

    @Override
    public Planet updatePlanetSize(Planet planet, Long id) {
        Planet oldPlanet = this.getById(id);
        oldPlanet.setHeight(planet.getHeight());
        oldPlanet.setWidth(planet.getWidth());
        oldPlanet.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return planetRepository.save(oldPlanet);
    }

    @Override
    public void delete(Long id) {
        Planet planet = this.getById(id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        planet.setUpdatedAt(timestamp);
        planet.setDeletedAt(timestamp);
        this.planetRepository.save(planet);
    }

    /**
     * @param page Page number. Starting at 0
     * @param limitPerPage Maximum number of records per page.
     * @param orderBy
     * @param
     * @return
     */
    @Override
    public Page<Planet> findPage(Integer page, Integer limitPerPage, String orderBy, String sort) {
        PageRequest pageRequest = PageRequest.of(page, limitPerPage, Sort.Direction.valueOf(sort), orderBy);
        return planetRepository.findAll(pageRequest);
    }

}
