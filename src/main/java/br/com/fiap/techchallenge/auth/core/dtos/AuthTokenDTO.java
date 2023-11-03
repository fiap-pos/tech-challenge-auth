package br.com.fiap.techchallenge.auth.core.dtos;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.TokenType;

public record AuthTokenDTO(String accessToken, TokenType tokenType, Long expiresIn) {
}
