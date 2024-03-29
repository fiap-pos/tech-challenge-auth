package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.domain.entities.AuthToken;
import br.com.fiap.techchallenge.auth.core.domain.entities.User;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.UnauthorizedException;
import br.com.fiap.techchallenge.auth.core.dtos.AuthCustomerDTO;
import br.com.fiap.techchallenge.auth.core.dtos.AuthTokenDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.in.AuthInputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;

public class AuthenticateUseCase implements AuthInputPort {

    private final GetUserOutputPort getUserOutputPort;
    private final AuthToken authToken;

    public AuthenticateUseCase(GetUserOutputPort getUserOutputPort, String authSecret) {
        this.getUserOutputPort = getUserOutputPort;
        this.authToken = new AuthToken(authSecret);
    }

    @Override
    public AuthTokenDTO authenticateCustomer(AuthCustomerDTO authDTO) throws UnauthorizedException {
        var userDTO = getUserOutputPort.getByUsername(authDTO.username());

        var user = new User(
                userDTO.id(),
                userDTO.name(),
                userDTO.username(),
                userDTO.email(),
                userDTO.active(),
                userDTO.roles()
        );

        if (Boolean.FALSE.equals(user.isActive())) {
            throw new UnauthorizedException("Usuário inativo.");
        }

        return authToken.create(user);
    }

    @Override
    public AuthTokenDTO authenticateGuest() {
        return authToken.createGuest();
    }

    @Override
    public UserDTO getUserByToken(String token) {
        var userDTO = authToken.getUser(token);
        if (!userDTO.roles().contains(UserRole.GUEST)) {
            return getUserOutputPort.getById(userDTO.id());
        }
        return userDTO;
    }
}
