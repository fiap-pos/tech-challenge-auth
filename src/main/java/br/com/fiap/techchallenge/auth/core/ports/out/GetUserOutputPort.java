package br.com.fiap.techchallenge.auth.core.ports.out;

import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface GetUserOutputPort {
    UserDTO getById(String id);
    UserDTO getByUsername(String username);
}
