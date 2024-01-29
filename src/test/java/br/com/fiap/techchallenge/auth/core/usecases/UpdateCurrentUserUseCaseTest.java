package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.domain.entities.AuthToken;
import br.com.fiap.techchallenge.auth.core.domain.entities.User;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.dtos.AuthTokenDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UpdateUserDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.UpdateUserOutputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static br.com.fiap.techchallenge.auth.Helpers.getUserDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class UpdateCurrentUserUseCaseTest {

    private final String authSecret = "signingKey";

    private UpdateCurrentUserUseCase updateCurrentUserUseCase;

    @Mock
    private UpdateUserOutputPort updateUserOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        updateCurrentUserUseCase = new UpdateCurrentUserUseCase(
                updateUserOutputPort,
                authSecret
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldUpdateUser() {

        var tokenDTO = getTokenDTO(getUser());
        var updateUserDTO = new UpdateUserDTO(
                "new-user-name",
                "new-user-email"
        );
        var userDTO = getUserDTO();

        when(updateUserOutputPort.update("user-id", updateUserDTO)).thenReturn(userDTO);

        var userUpdated = updateCurrentUserUseCase.update(tokenDTO.accessToken(), updateUserDTO);

        assertThat(userUpdated).isNotNull().isInstanceOf(UserDTO.class);
        assertThat(userUpdated.id()).isEqualTo(userDTO.id());
        assertThat(userUpdated.name()).isEqualTo(userDTO.name());
        assertThat(userUpdated.username()).isEqualTo(userDTO.username());
        assertThat(userUpdated.email()).isEqualTo(userDTO.email());
        assertThat(userUpdated.roles()).isEqualTo(userDTO.roles());

        verify(updateUserOutputPort, times(1)).update("user-id", updateUserDTO);
    }

    private AuthTokenDTO getTokenDTO(User user) {
        var authToken = new AuthToken(authSecret);
        return authToken.create(user);
    }

    private User getUser() {
        return new User(
                "user-id",
                "user-name",
                "user-username",
                "user-email",
                List.of(UserRole.CUSTOMER)
        );
    }
}
