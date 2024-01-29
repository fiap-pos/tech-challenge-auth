package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class GetUserUseCaseTest {

    private GetUserUseCase getUserUseCase;

    @Mock
    private GetUserOutputPort getUserOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        getUserUseCase = new GetUserUseCase(
                getUserOutputPort
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldGetUser() {
        var user = getUserDTO();

        when(getUserOutputPort.getById(user.id())).thenReturn(user);

        var userFound = getUserUseCase.getById(user.id());

        assertThat(userFound).isNotNull().isInstanceOf(UserDTO.class);
        assertThat(userFound.id()).isEqualTo(user.id());
        assertThat(userFound.name()).isEqualTo(user.name());
        assertThat(userFound.username()).isEqualTo(user.username());
        assertThat(userFound.email()).isEqualTo(user.email());
        assertThat(userFound.roles()).isEqualTo(user.roles());

        verify(getUserOutputPort, times(1)).getById(user.id());
        verifyNoMoreInteractions(getUserOutputPort);
    }

    private UserDTO getUserDTO() {
        return new UserDTO(
                "user-id",
                "user-name",
                "user-username",
                "user-email",
                List.of(UserRole.CUSTOMER)
        );
    }
}
