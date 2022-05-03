package br.com.elo7.sonda.candidato.domain.exceptions.type;

public class BusinessException extends RuntimeException {

    public BusinessException(String s) {
        super(s);
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
