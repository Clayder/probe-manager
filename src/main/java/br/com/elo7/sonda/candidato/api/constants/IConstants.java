package br.com.elo7.sonda.candidato.api.constants;

public interface IConstants {

    public interface Controller {
        String PATH = "/api/v1/";

         public interface Planet {
             String SLUG_PATH = PATH + "planets";
         }
    }

    public interface MessageError {

        public interface Default {
             String REQUIRED_FIELD = "Required field.";
             String GREATER_THAN_ZERO = "Value must be greater than 0";
         }

        public interface Planet extends Default {
            int NAME_SIZE_MIN = 2;
            int NAME_SIZE_MAX = 15;
            String NAME_LENGTH_FIELD = "The length must be between " + NAME_SIZE_MIN + " and " + NAME_SIZE_MAX + " characters.";

        }
    }

}
