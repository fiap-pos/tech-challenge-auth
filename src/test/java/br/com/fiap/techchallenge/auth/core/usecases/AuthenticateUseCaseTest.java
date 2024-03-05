package br.com.fiap.techchallenge.auth.core.usecases;


import br.com.fiap.techchallenge.auth.core.domain.entities.AuthToken;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.TokenType;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.UnauthorizedException;
import br.com.fiap.techchallenge.auth.core.dtos.AuthCustomerDTO;
import br.com.fiap.techchallenge.auth.core.dtos.AuthTokenDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static br.com.fiap.techchallenge.auth.Helpers.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
class AuthenticateUseCaseTest {

    private final String authSecret = "signingKey";

    private AuthenticateUseCase authenticateUseCase;

    @Mock
    private GetUserOutputPort getUserOutputPort;

    AutoCloseable mock;


    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        authenticateUseCase = new AuthenticateUseCase(
                getUserOutputPort,
                authSecret
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldAuthenticateCustomer() {
        var authDTO = new AuthCustomerDTO("username");
        var userDTO = getUserDTO();

        when(getUserOutputPort.getByUsername(authDTO.username())).thenReturn(userDTO);

        var tokenDTO = authenticateUseCase.authenticateCustomer(authDTO);

        assertThat(tokenDTO).isNotNull().isInstanceOf(AuthTokenDTO.class);
        assertThat(tokenDTO.accessToken()).isNotNull().isNotBlank();
        assertThat(tokenDTO.expiresIn()).isNotNull().isPositive();
        assertThat(tokenDTO.tokenType()).isNotNull();
        assertThat(tokenDTO.tokenType()).isEqualTo(TokenType.BEARER);
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenAuthenticateCustomerWithInactiveUser() {
        var authDTO = new AuthCustomerDTO("username");
        var userDTO = getInactiveUserDTO();

        when(getUserOutputPort.getByUsername(authDTO.username())).thenReturn(userDTO);

        var exception = assertThrows(UnauthorizedException.class, () -> {
            authenticateUseCase.authenticateCustomer(authDTO);
        });

        assertThat(exception).isNotNull().isInstanceOf(UnauthorizedException.class);
        assertThat(exception.getMessage()).isEqualTo("Usuário inativo.");
    }


    @Test
    void shouldAuthenticateGuestUser() {
        var tokenDTO = authenticateUseCase.authenticateGuest();

        assertThat(tokenDTO).isNotNull().isInstanceOf(AuthTokenDTO.class);
        assertThat(tokenDTO.accessToken()).isNotNull().isNotBlank();
        assertThat(tokenDTO.expiresIn()).isNotNull().isPositive();
        assertThat(tokenDTO.tokenType()).isNotNull();
        assertThat(tokenDTO.tokenType()).isEqualTo(TokenType.BEARER);
    }


    @Test
    void shouldGetCustomerUserByToken() {
        var userDTO = getUserDTO();
        var user = getUser(userDTO.id());

        var authToken = (new AuthToken(authSecret)).create(user);

        when(getUserOutputPort.getById(userDTO.id())).thenReturn(userDTO);

        var customerUser = authenticateUseCase.getUserByToken(authToken.accessToken());

        assertThat(userDTO).isNotNull().isInstanceOf(UserDTO.class);
        assertThat(userDTO.id()).isEqualTo(customerUser.id());
        assertThat(userDTO.name()).isEqualTo(getUserDTO().name());
        assertThat(userDTO.username()).isEqualTo(getUserDTO().username());
        assertThat(userDTO.email()).isEqualTo(getUserDTO().email());
        assertThat(userDTO.roles()).isEqualTo(getUserDTO().roles());
    }

    @Test
    void shouldGetGuestUserByToken() {
        var authToken = (new AuthToken(authSecret)).createGuest();

        var guestUser = authenticateUseCase.getUserByToken(authToken.accessToken());

        assertThat(guestUser).isNotNull().isInstanceOf(UserDTO.class);
        assertThat(guestUser.id()).isNotNull();
        assertThat(guestUser.name()).isNull();
        assertThat(guestUser.username()).isNull();
        assertThat(guestUser.email()).isNull();
        assertThat(guestUser.roles()).isEqualTo(List.of(UserRole.GUEST));
    }

}
