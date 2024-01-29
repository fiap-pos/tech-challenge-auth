package br.com.fiap.techchallenge.auth.adapters.web;

import br.com.fiap.techchallenge.auth.adapters.web.models.requests.AuthenticateCustomerRequest;
import br.com.fiap.techchallenge.auth.adapters.web.models.responses.TokenResponse;
import br.com.fiap.techchallenge.auth.adapters.web.models.responses.UserResponse;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.TokenType;
import br.com.fiap.techchallenge.auth.core.dtos.AuthTokenDTO;
import br.com.fiap.techchallenge.auth.core.ports.in.AuthInputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static br.com.fiap.techchallenge.auth.Helpers.getUserDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    AutoCloseable mock;

    @Mock
    private AuthInputPort authInputPort;

    private AuthController authController;


    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        authController = new AuthController(authInputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldAuthenticateGuest() {
        var authTokenDTO = getAuthTokenDTO();
        when(authInputPort.authenticateGuest()).thenReturn(authTokenDTO);

        var response = authController.authenticateGuest();

        var tokenResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(tokenResponse).isNotNull().isInstanceOf(TokenResponse.class);
        assertThat(tokenResponse.getAccessToken()).isEqualTo(authTokenDTO.accessToken());
        assertThat(tokenResponse.getType()).isEqualTo(authTokenDTO.tokenType().toString().toLowerCase());
        assertThat(tokenResponse.getExpiresIn()).isEqualTo(authTokenDTO.expiresIn());
    }

    @Test
    void shouldAuthenticateCustomer() {
        var authenticateCustomerRequest = new AuthenticateCustomerRequest();
        authenticateCustomerRequest.setUsername("username");
        var authTokenDTO = getAuthTokenDTO();

        when(authInputPort.authenticateCustomer(authenticateCustomerRequest.toAuthCustomerDTO())).thenReturn(authTokenDTO);

        var response = authController.authenticate(authenticateCustomerRequest);

        var tokenResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(tokenResponse).isNotNull().isInstanceOf(TokenResponse.class);
        assertThat(tokenResponse.getAccessToken()).isEqualTo(authTokenDTO.accessToken());
        assertThat(tokenResponse.getType()).isEqualTo(authTokenDTO.tokenType().toString().toLowerCase());
        assertThat(tokenResponse.getExpiresIn()).isEqualTo(authTokenDTO.expiresIn());
    }

    @Test
    void shouldGetUserInfo() {
        var userDTO = getUserDTO();
        when(authInputPort.getUserByToken("token")).thenReturn(userDTO);

        var response = authController.info("token");
        var userResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse).isNotNull().isInstanceOf(UserResponse.class);
        assertThat(userResponse.getId()).isEqualTo(userDTO.id());
        assertThat(userResponse.getName()).isEqualTo(userDTO.name());
        assertThat(userResponse.getUsername()).isEqualTo(userDTO.username());
        assertThat(userResponse.getEmail()).isEqualTo(userDTO.email());
        assertThat(userResponse.getRoles()).isEqualTo(userDTO.roles());
    }

    private AuthTokenDTO getAuthTokenDTO() {
        return new AuthTokenDTO(
                "accessToken",
                TokenType.BEARER,
                3600L
                );
    }
}