package br.com.elo7.sonda.candidato.domain.exceptions.type;

import org.slf4j.Logger;

public class ObjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ObjectNotFoundException(String msg, Logger logger) {
        super(msg);
        logger.error(msg);
    }

    public ObjectNotFoundException(String msg, Throwable cause, Logger logger) {
        super(msg, cause);
        logger.error(msg);
    }

}

