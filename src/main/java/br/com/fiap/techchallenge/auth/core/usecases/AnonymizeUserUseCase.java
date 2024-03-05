package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.domain.entities.User;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.auth.core.dtos.AnonymizeRequestDTO;
import br.com.fiap.techchallenge.auth.core.dtos.CreateAnonymizeRequestDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.in.AnonymizeUserInputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateAnonymizeRequestOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.UpdateUserOutputPort;

import java.time.LocalDateTime;
import java.util.UUID;

public class AnonymizeUserUseCase implements AnonymizeUserInputPort {

    private final GetUserOutputPort getUserOutputPort;

    private final UpdateUserOutputPort updateUserOutputPort;

    private final CreateAnonymizeRequestOutputPort createAnonymizeRequestOutputPort;

    public AnonymizeUserUseCase(
            GetUserOutputPort getUserOutputPort,
            UpdateUserOutputPort updateUserOutputPort,
            CreateAnonymizeRequestOutputPort createAnonymizeRequestOutputPort
    ) {
        this.getUserOutputPort = getUserOutputPort;
        this.updateUserOutputPort = updateUserOutputPort;
        this.createAnonymizeRequestOutputPort = createAnonymizeRequestOutputPort;
    }

    @Override
    public UserDTO anonymize(String id, CreateAnonymizeRequestDTO createAnonymizeRequestDTO) throws EntityNotFoundException {
        var user = getUserFromDTO(getUserOutputPort.getById(id));

        createAnonymizeRequest(createAnonymizeRequestDTO);
        anonymize(user);

        var userDTO = new UserDTO(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.isActive(),
                user.getRoles()
        );

        return updateUserOutputPort.update(user.getId(), userDTO);
    }

    private void createAnonymizeRequest(CreateAnonymizeRequestDTO createAnonymizeRequestDTO) {
        var anonymizeRequestDTO = new AnonymizeRequestDTO(
                createAnonymizeRequestDTO.name(),
                createAnonymizeRequestDTO.phone(),
                createAnonymizeRequestDTO.address(),
                LocalDateTime.now()
        );
        createAnonymizeRequestOutputPort.create(anonymizeRequestDTO);
    }

    private User getUserFromDTO(UserDTO userDTO) {
        return new User(userDTO.id(), userDTO.name(), userDTO.username(), userDTO.email(), userDTO.active(), userDTO.roles());
    }

    private void anonymize(User user) {
        user.setActive(false);
        user.setName("Anônimo");
        user.setEmail("anonimo@techchallenge.com");
        user.setUsername(UUID.randomUUID().toString());
    }
}
