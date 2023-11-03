package br.com.fiap.techchallenge.auth.core.ports.in;

import br.com.fiap.techchallenge.auth.core.dtos.UpdateUserDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface UpdateCurrentUserInputPort {
    public UserDTO update(String token, UpdateUserDTO updateUserDTO);
}
