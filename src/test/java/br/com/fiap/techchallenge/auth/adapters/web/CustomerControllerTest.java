package br.com.fiap.techchallenge.auth.adapters.web;

import br.com.fiap.techchallenge.auth.adapters.web.models.requests.CreateCustomerRequest;
import br.com.fiap.techchallenge.auth.adapters.web.models.responses.UserResponse;
import br.com.fiap.techchallenge.auth.core.ports.in.CreateCustomerUserInputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static br.com.fiap.techchallenge.auth.Helpers.getUserDTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    AutoCloseable mock;

    @Mock
    private CreateCustomerUserInputPort createCustomerUserInputPort;

    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        customerController = new CustomerController(createCustomerUserInputPort);
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldCreateCurstomer() {
        var request = new CreateCustomerRequest();
        request.setName("name");
        request.setCpf("cpf");
        request.setEmail("email");

        var userDTO = getUserDTO();

        when(createCustomerUserInputPort.create(request.toCreateCustomerDTO())).thenReturn(userDTO);

        var response = customerController.create(request);
        var userResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(userResponse).isNotNull().isInstanceOf(UserResponse.class);
        assertThat(userResponse.getId()).isEqualTo(userDTO.id());
        assertThat(userResponse.getName()).isEqualTo(userDTO.name());
        assertThat(userResponse.getEmail()).isEqualTo(userDTO.email());
        assertThat(userResponse.getUsername()).isEqualTo(userDTO.username());
    }

}