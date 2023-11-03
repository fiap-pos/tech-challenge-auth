package br.com.fiap.techchallenge.auth.adapters.web;

import br.com.fiap.techchallenge.auth.adapters.web.models.requests.CreateCustomerRequest;
import br.com.fiap.techchallenge.auth.adapters.web.models.responses.UserResponse;
import br.com.fiap.techchallenge.auth.core.ports.in.CreateCustomerUserInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Tag(name = "Customers", description = "APIs para gerenciamento dos clientes que irão se autenticar")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CreateCustomerUserInputPort createCustomerUserInputPort;

    public CustomerController(CreateCustomerUserInputPort createCustomerUserInputPort) {
        this.createCustomerUserInputPort = createCustomerUserInputPort;
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário do tipo customer")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateCustomerRequest request) {

        var user = createCustomerUserInputPort.create(request.toCreateCustomerDTO());
        var uri =  ServletUriComponentsBuilder.fromPath("/users/{id}")
                        .buildAndExpand(user.id())
                        .toUri();

        return ResponseEntity.created(uri).body(new UserResponse(user));
    }
}

