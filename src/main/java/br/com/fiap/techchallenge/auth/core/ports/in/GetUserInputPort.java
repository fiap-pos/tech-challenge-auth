package br.com.fiap.techchallenge.auth.core.ports.in;

import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface GetUserInputPort {
    public UserDTO getById(String id);
}
