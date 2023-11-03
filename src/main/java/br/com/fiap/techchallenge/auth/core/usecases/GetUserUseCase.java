package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.in.GetUserInputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;

public class GetUserUseCase implements GetUserInputPort {

    private final GetUserOutputPort getUserOutputPort;

    public GetUserUseCase(GetUserOutputPort getUserOutputPort) {
        this.getUserOutputPort = getUserOutputPort;
    }

    @Override
    public UserDTO getById(String id) {
        return getUserOutputPort.getById(id);
    }
}
