package br.com.elo7.sonda.candidato.domain.exceptions.type;

import org.slf4j.Logger;

public class InternalErrorException extends RuntimeException {

    public InternalErrorException(String s, Logger logger) {
        super(s);
        logger.error(s);
    }

    public InternalErrorException(String msg, Throwable cause, Logger logger) {
        super(msg, cause);
        logger.error(msg);
    }
}