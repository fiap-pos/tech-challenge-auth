package br.com.fiap.techchallenge.auth.core.ports.in;

import br.com.fiap.techchallenge.auth.core.dtos.AuthCustomerDTO;
import br.com.fiap.techchallenge.auth.core.dtos.AuthTokenDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface AuthInputPort {
    public AuthTokenDTO authenticateCustomer(AuthCustomerDTO authDTO);

    public AuthTokenDTO authenticateGuest();

    public UserDTO getUserByToken(String token);
}
