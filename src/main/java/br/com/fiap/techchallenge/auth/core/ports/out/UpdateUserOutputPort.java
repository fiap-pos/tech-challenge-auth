package br.com.fiap.techchallenge.auth.core.ports.out;

import br.com.fiap.techchallenge.auth.core.dtos.UpdateUserDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface UpdateUserOutputPort {
    public UserDTO update(String id, UpdateUserDTO userDTO);
    public UserDTO update(String id, UserDTO userDTO);
}
