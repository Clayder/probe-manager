package br.com.elo7.sonda.candidato.api.constants;

public interface IConstants {

    interface Controller {
        String VERSION = "/api/v1";
        String SLUG = "{id}";

        interface Planet {
            String NAME = "/planets";
            String PATH = VERSION + NAME;
            String PLANET_POBE = SLUG + Probe.NAME ;
            String PLANET_POBE_ID = PLANET_POBE + "/{probe_id}" ;
        }

        interface Probe {
            String NAME = "/probes";
            String PATH = VERSION + NAME;
        }
    }

    interface MessageError {

        interface Default {
            String REQUIRED_FIELD = "Required field.";
            String GREATER_THAN_ZERO = "Value must be greater than 0";
        }

        interface Planet extends Default {
            int NAME_SIZE_MIN = 2;
            int NAME_SIZE_MAX = 15;
            String NAME_LENGTH_FIELD = "The length must be between " + NAME_SIZE_MIN + " and " + NAME_SIZE_MAX + " characters.";

        }
    }

}
