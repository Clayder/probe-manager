package br.com.elo7.sonda.candidato.domain.exceptions.type;

public class InternalErrorException extends RuntimeException {

    public InternalErrorException(String s) {
        super(s);
    }

    public InternalErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}