package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.dtos.AnonymizeRequestDTO;
import br.com.fiap.techchallenge.auth.core.dtos.CreateAnonymizeRequestDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateAnonymizeRequestOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.UpdateUserOutputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AnonymizeUserUseCaseTest {

    private AnonymizeUserUseCase anonymizeUserUseCase;

    @Mock
    private GetUserOutputPort getUserOutputPort;

    @Mock
    private UpdateUserOutputPort updateUserOutputPort;

    @Mock
    private CreateAnonymizeRequestOutputPort createAnonymizeRequestOutputPort;

    @Captor
    ArgumentCaptor<UserDTO> updatedUserCaptor;

    AutoCloseable mock;

    @BeforeEach
    public void setup() {
        this.mock = MockitoAnnotations.openMocks(this);
        this.anonymizeUserUseCase = new AnonymizeUserUseCase(
                getUserOutputPort,
                updateUserOutputPort,
                createAnonymizeRequestOutputPort
        );
    }

    @Test
    void shouldAnonymize() {

        var userID = "user-id";

        var createAnonymizeRequest = new CreateAnonymizeRequestDTO(
                "username",
                "11999999999",
                "User full address"
        );

        var userDTO = new UserDTO(
                userID,
                "user-name",
                "user-username",
                "user-email",
                true,
                List.of(UserRole.CUSTOMER)
        );

        when(getUserOutputPort.getById(userID)).thenReturn(userDTO);
        when(createAnonymizeRequestOutputPort.create(any(AnonymizeRequestDTO.class))).thenReturn(mock(AnonymizeRequestDTO.class));
        when(updateUserOutputPort.update(eq(userID), any(UserDTO.class))).thenReturn(userDTO);

        var anonymousUser = anonymizeUserUseCase.anonymize(userID, createAnonymizeRequest);

        assertThat(anonymousUser).isNotNull().isEqualTo(userDTO);
        verify(updateUserOutputPort, times(1)).update(eq(userID), updatedUserCaptor.capture());

        var updatedUser = updatedUserCaptor.getValue();
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.id()).isEqualTo(userID);
        assertThat(updatedUser.name()).isEqualTo("Anônimo");
        assertThat(updatedUser.username()).isNotEqualTo("user-username");
        assertThat(updatedUser.email()).isEqualTo("anonimo@techchallenge.com");
        assertThat(updatedUser.active()).isFalse();
        assertThat(updatedUser.roles()).isEqualTo(List.of(UserRole.CUSTOMER));
    }

    @AfterEach
    public void tearDown() throws Exception {
        mock.close();
    }
}