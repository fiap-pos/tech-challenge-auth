package br.com.fiap.techchallenge.auth.adapters.web;

import br.com.fiap.techchallenge.auth.adapters.web.models.requests.AnonymizeUserRequest;
import br.com.fiap.techchallenge.auth.adapters.web.models.requests.UpdateUserRequest;
import br.com.fiap.techchallenge.auth.adapters.web.models.responses.UserResponse;
import br.com.fiap.techchallenge.auth.core.ports.in.AnonymizeUserInputPort;
import br.com.fiap.techchallenge.auth.core.ports.in.GetUserInputPort;
import br.com.fiap.techchallenge.auth.core.ports.in.UpdateCurrentUserInputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static br.com.fiap.techchallenge.auth.Helpers.getUserDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class UserControllerTest {

    AutoCloseable mock;

    @Mock
    private GetUserInputPort getUserInputPort;

    @Mock
    private UpdateCurrentUserInputPort updateCurrentUserInputPort;

    @Mock
    private AnonymizeUserInputPort anonymizeUserInputPort;

    private UserController userController;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        userController = new UserController(getUserInputPort, updateCurrentUserInputPort, anonymizeUserInputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }


    @Test
    void shouldGetUser() {
        var userDTO = getUserDTO();

        when(getUserInputPort.getById("user-id")).thenReturn(userDTO);

        var response = userController.get("user-id");

        var userResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse).isNotNull().isInstanceOf(UserResponse.class);
        assertThat(userResponse.getId()).isEqualTo(userDTO.id());
        assertThat(userResponse.getName()).isEqualTo(userDTO.name());
        assertThat(userResponse.getEmail()).isEqualTo(userDTO.email());
        assertThat(userResponse.getUsername()).isEqualTo(userDTO.username());

    }

    @Test
    void shouldUpdateCurrentUser() {
        var request = new UpdateUserRequest();
        request.setName("name");
        request.setEmail("email");

        var userDTO = getUserDTO();

        when(updateCurrentUserInputPort.update("token", request.toUpdateUserDTO())).thenReturn(userDTO);

        var response = userController.update(request, "token");
        var userResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse).isNotNull().isInstanceOf(UserResponse.class);
        assertThat(userResponse.getId()).isEqualTo(userDTO.id());
        assertThat(userResponse.getName()).isEqualTo(userDTO.name());
        assertThat(userResponse.getEmail()).isEqualTo(userDTO.email());
        assertThat(userResponse.getUsername()).isEqualTo(userDTO.username());
    }

    @Test
    void shouldAnonymizeCurrentUser() {
        var userDTO = getUserDTO();
        var request = new AnonymizeUserRequest(
                "username",
                "11999999999",
                "User full address"
        );

        when(anonymizeUserInputPort.anonymize(userDTO.id(), request.toCreateAnonymizeRequestDTO())).thenReturn(userDTO);

        var response = userController.anonymize(request, userDTO.id());

        var userResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResponse).isNotNull().isInstanceOf(UserResponse.class);
        assertThat(userResponse.getId()).isEqualTo(userDTO.id());
        assertThat(userResponse.getName()).isEqualTo(userDTO.name());
        assertThat(userResponse.getEmail()).isEqualTo(userDTO.email());
        assertThat(userResponse.getUsername()).isEqualTo(userDTO.username());
    }
}