package br.com.fiap.techchallenge.auth.core.ports.in;

import br.com.fiap.techchallenge.auth.core.domain.exceptions.UnauthorizedException;
import br.com.fiap.techchallenge.auth.core.dtos.AuthCustomerDTO;
import br.com.fiap.techchallenge.auth.core.dtos.AuthTokenDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface AuthInputPort {
    public AuthTokenDTO authenticateCustomer(AuthCustomerDTO authDTO) throws UnauthorizedException;

    public AuthTokenDTO authenticateGuest();

    public UserDTO getUserByToken(String token);
}
