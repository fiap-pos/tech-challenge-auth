package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityAlreadyExistException;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.auth.core.dtos.CreateCustomerDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.in.CreateCustomerUserInputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;

import java.util.ArrayList;

public class CreateCustomerUserUseCase implements CreateCustomerUserInputPort {

    private final CreateUserOutputPort createUserOutputPort;
    private final GetUserOutputPort getUserOutputPort;

    public CreateCustomerUserUseCase(CreateUserOutputPort createUserOutputPort, GetUserOutputPort getUserOutputPort) {
        this.createUserOutputPort = createUserOutputPort;
        this.getUserOutputPort = getUserOutputPort;
    }

    public UserDTO create(CreateCustomerDTO customerIn) {
        var userDTO = new UserDTO(
            customerIn.name(),
            customerIn.username(),
            customerIn.email(),
            true,
            getCustomerRoles()
        );
        validateUsername(userDTO);
        return createUserOutputPort.create(userDTO);
    }

    private ArrayList<UserRole> getCustomerRoles() {
        var roles = new ArrayList<UserRole>();
        roles.add(UserRole.CUSTOMER);
        return roles;
    }

    private void validateUsername(UserDTO userDTO) {
        try {
            getUserOutputPort.getByUsername(userDTO.username());
            throw new EntityAlreadyExistException("Já existe um usuário com o username informado");
        } catch(EntityNotFoundException ignored) {

        }
    }
}
