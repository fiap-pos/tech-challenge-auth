package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.dtos.AnonymizeRequestDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateAnonymizeRequestOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.UpdateUserOutputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

class AnonymizeDataUseCaseTest {

    private AnonymizeUserUseCase anonymizeDataUseCase;

    @Mock
    private GetUserOutputPort getUserOutputPort;

    @Mock
    private UpdateUserOutputPort updateUserOutputPort;

    @Mock
    private CreateAnonymizeRequestOutputPort createAnonymizeRequestOutputPort;

    AutoCloseable mock;

    @BeforeEach
    public void setup() {
        this.mock = MockitoAnnotations.openMocks(this);
        this.anonymizeDataUseCase = new AnonymizeUserUseCase(
                getUserOutputPort,
                updateUserOutputPort,
                createAnonymizeRequestOutputPort
        );
    }

    @Test
    public void shouldAnonymizeUserData() {
        var anonimizeRequest = new AnonymizeRequestDTO(
                "username",
                "11999999999",
                "User full address",
                LocalDateTime.now()
        );
    }

    @AfterEach
    public void tearDown() throws Exception {
        mock.close();
    }
}