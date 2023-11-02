package br.com.fiap.techchallenge.auth.core.ports.out;

import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface CreateUserOutputPort {
    public UserDTO create(UserDTO userIn);
}
