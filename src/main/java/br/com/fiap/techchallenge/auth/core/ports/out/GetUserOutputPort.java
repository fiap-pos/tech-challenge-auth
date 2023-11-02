package br.com.fiap.techchallenge.auth.core.ports.out;

import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface GetUserOutputPort {
    public UserDTO getById(String id);
    public UserDTO getByUsername(String username);
}
