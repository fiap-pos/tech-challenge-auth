package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.domain.entities.AuthToken;
import br.com.fiap.techchallenge.auth.core.dtos.UpdateUserDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.in.UpdateCurrentUserInputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.UpdateUserOutputPort;

public class UpdateCurrentUserUseCase implements UpdateCurrentUserInputPort {

    private final String authSecret;

    private final UpdateUserOutputPort updateUserOutputPort;

    public UpdateCurrentUserUseCase(UpdateUserOutputPort updateUserOutputPort, String authSecret) {
        this.authSecret = authSecret;
        this.updateUserOutputPort = updateUserOutputPort;
    }

    @Override
    public UserDTO update(String token, UpdateUserDTO updateUserDTO) {
        var authToken = new AuthToken(authSecret);
        var userDTO = authToken.getUser(token);
        return updateUserOutputPort.update(userDTO.id(), updateUserDTO);
    }
}
