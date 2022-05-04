package br.com.elo7.sonda.candidato.api.config;

import br.com.elo7.sonda.candidato.api.dto.probe.ProbeDTO;
import br.com.elo7.sonda.candidato.api.model.Planet;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IPlanetEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.entities.IProbeEntity;
import br.com.elo7.sonda.candidato.domain.probemanager.factory.PlanetEntityFactory;
import br.com.elo7.sonda.candidato.domain.probemanager.factory.ProbeEntityFactory;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        Converter<ProbeDTO, IProbeEntity> toUpperCase = new Converter<ProbeDTO, IProbeEntity>() {
            public IProbeEntity convert(MappingContext<ProbeDTO, IProbeEntity> context) {
                ProbeDTO s = context.getSource();
                return ProbeEntityFactory.create(s.getX(), s.getY(), s.getDirection(), s.getCommands());
            }
        };
        modelMapper.addConverter(toUpperCase);

        Converter<Planet, IPlanetEntity> toUpperCase2 = new Converter<Planet, IPlanetEntity>() {
            public IPlanetEntity convert(MappingContext<Planet, IPlanetEntity> context) {
                Planet s = context.getSource();
                return PlanetEntityFactory.create(s.getId(), s.getName(), s.getWidth(), s.getHeight());
            }
        };
        modelMapper.addConverter(toUpperCase2);

        return modelMapper;
    }
}
