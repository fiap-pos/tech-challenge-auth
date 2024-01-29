package br.com.fiap.techchallenge.auth.core.usecases;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityAlreadyExistException;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.auth.core.dtos.CreateCustomerDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateCustomerUserUseCaseTest {

    private CreateCustomerUserUseCase createCustomerUserUseCase;

    @Mock
    private CreateUserOutputPort createUserOutputPort;
    @Mock
    private GetUserOutputPort getUserOutputPort;

    AutoCloseable mock;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        createCustomerUserUseCase = new CreateCustomerUserUseCase(
                createUserOutputPort,
                getUserOutputPort
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldCreateCustomerUser() {
        var customerDTO = getCustumerDTO();

        var user = new UserDTO(
                "user-id",
                customerDTO.name(),
                customerDTO.username(),
                customerDTO.email(),
                List.of(UserRole.CUSTOMER)
        );

        when(getUserOutputPort.getByUsername(any())).thenThrow(EntityNotFoundException.class);
        when(createUserOutputPort.create(any(UserDTO.class))).thenReturn(user);

        var createdCustomer = createCustomerUserUseCase.create(customerDTO);

        assertThat(createdCustomer).isNotNull();
        assertThat(createdCustomer.id()).isEqualTo(user.id());
        assertThat(createdCustomer.name()).isEqualTo(user.name());
        assertThat(createdCustomer.username()).isEqualTo(user.username());
        assertThat(createdCustomer.email()).isEqualTo(user.email());
        assertThat(createdCustomer.roles()).isEqualTo(user.roles());
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        var customerDTO = getCustumerDTO();

        var user = new UserDTO(
                "user-id",
                customerDTO.name(),
                customerDTO.username(),
                customerDTO.email(),
                List.of(UserRole.CUSTOMER)
        );

        when(getUserOutputPort.getByUsername(any())).thenReturn(user);

        var exception = assertThrows(
                EntityAlreadyExistException.class,
                () -> createCustomerUserUseCase.create(customerDTO)
        );

        assertThat(exception.getMessage()).isEqualTo("Já existe um usuário com o username informado");
    }

    private CreateCustomerDTO getCustumerDTO() {
        return new CreateCustomerDTO(
                "João da Silva",
                "12345678910",
                "joao.silva@email.com"
        );
    }
}
