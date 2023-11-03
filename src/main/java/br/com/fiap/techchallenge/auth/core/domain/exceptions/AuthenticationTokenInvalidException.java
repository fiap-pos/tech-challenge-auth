package br.com.fiap.techchallenge.auth.core.domain.exceptions;

public class AuthenticationTokenInvalidException extends RuntimeException {
    public AuthenticationTokenInvalidException(String message) {
        super(message);
    }

    public AuthenticationTokenInvalidException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AuthenticationTokenInvalidException(Throwable throwable) {
        super(throwable);
    }
}
