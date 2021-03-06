package br.com.elo7.sonda.candidato.api.service.impl;


import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.api.model.Probe;
import br.com.elo7.sonda.candidato.api.repository.IProbeRepository;
import br.com.elo7.sonda.candidato.api.service.IProbeService;
import br.com.elo7.sonda.candidato.domain.exceptions.messages.ErrorMessage;
import br.com.elo7.sonda.candidato.domain.exceptions.type.BusinessException;
import br.com.elo7.sonda.candidato.domain.exceptions.type.ObjectNotFoundException;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProbeService implements IProbeService {

    private IProbeRepository probeRepository;

    private ModelMapper modelMapper;

    private static Logger logger = LoggerFactory.getLogger(ProbeService.class);

    public ProbeService(IProbeRepository probeRepository, ModelMapper modelMapper) {
        this.probeRepository = probeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Probe> convertAndMoveProbes(IPlanetEntity planetEntity, Planet planetModel) {
        return planetEntity.getProbes()
                .stream().map(
                        probeEntity -> {
                            logger.debug(String.format(
                                    "Planet %d Probe X_ORIG: %d Y_ORIG: %d",
                                    planetModel.getId(), probeEntity.getX(), probeEntity.getY()
                            ));
                            moveProbeWithAllCommands(probeEntity, planetEntity);
                            return this.insert(probeEntity, planetModel);
                        }
                ).collect(Collectors.toList());
    }

    @Override
    public Probe moveProbe(IProbeEntity probeEntity, IPlanetEntity planetEntity, Probe probeModel) {

        moveProbeWithAllCommands(probeEntity, planetEntity);

        probeModel.setX(probeEntity.getX());
        probeModel.setY(probeEntity.getY());
        probeModel.setDirection(probeEntity.getDirection());
        probeModel.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        checkCollision(probeEntity, probeModel.getPlanet());

        return this.probeRepository.save(probeModel);

    }

    public Probe insert(IProbeEntity probeEntity, Planet planetModel) {

        boolean isCollision = probeRepository.existsProbeByPlanetAndXAndYAndDeletedAtIsNull(
                planetModel,
                probeEntity.getX(),
                probeEntity.getY()
        );

        if (isCollision) {
            throw new BusinessException(ErrorMessage.AVOID_COLLISION_BETWEEN_PROBES, logger);
        }

        Probe probe = modelMapper.map(probeEntity, Probe.class);
        probe.setPlanet(planetModel);
        probe.setActive(true);
        probe.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return probeRepository.save(probe);
    }

    public void checkCollision(IProbeEntity probeEntity, Planet planetModel) {
        boolean isCollision = probeRepository.existsProbeByPlanetAndXAndYAndIdNotAndDeletedAtIsNull(
                planetModel,
                probeEntity.getX(),
                probeEntity.getY(),
                probeEntity.getId()
        );

        if (isCollision) {
            throw new BusinessException(ErrorMessage.AVOID_COLLISION_BETWEEN_PROBES, logger);
        }
    }

    @Override
    public List<Probe> convertListIProbeEntyToListProbe(List<IProbeEntity> probeEntities) {
        return modelMapper.map(probeEntities, new TypeToken<List<Probe>>() {
        }.getType());
    }

    @Override
    public Probe getById(Long id) {
        Optional<Probe> obj = probeRepository.findById(id);
        Probe probe = obj.orElseThrow(() -> new ObjectNotFoundException(ErrorMessage.PROBE_NOT_FOUND, logger));
        if (probe.getDeletedAt() != null) {
            throw new ObjectNotFoundException(ErrorMessage.PROBE_NOT_FOUND, logger);
        }
        return probe;
    }

    @Override
    public void delete(Long id) {
        Probe probe = this.getById(id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        probe.setUpdatedAt(timestamp);
        probe.setDeletedAt(timestamp);
        probe.setActive(false);
        this.probeRepository.save(probe);
    }

    /**
     * @param page         Page number. Starting at 0
     * @param limitPerPage Maximum number of records per page.
     * @param orderBy
     * @param
     * @return
     */
    @Override
    public Page<Probe> findPage(Integer page, Integer limitPerPage, String orderBy, String sort) {
        PageRequest pageRequest = PageRequest.of(page, limitPerPage, Sort.Direction.valueOf(sort), orderBy);
        return probeRepository.findAll(pageRequest);
    }

    private void moveProbeWithAllCommands(IProbeEntity probeEntity, IPlanetEntity planetEntity) {
        for (char command : probeEntity.getCommands().toCharArray()) {
            logger.debug(String.format("Running the command: %c", command));
            probeEntity.applyCommandToProbe(command, planetEntity);
        }
    }


}
