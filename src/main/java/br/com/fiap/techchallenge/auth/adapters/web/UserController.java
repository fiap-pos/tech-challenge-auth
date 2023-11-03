package br.com.fiap.techchallenge.auth.adapters.web;

import br.com.fiap.techchallenge.auth.adapters.web.models.requests.UpdateUserRequest;
import br.com.fiap.techchallenge.auth.adapters.web.models.responses.UserResponse;
import br.com.fiap.techchallenge.auth.core.domain.entities.User;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.in.GetUserInputPort;
import br.com.fiap.techchallenge.auth.core.ports.in.UpdateCurrentUserInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "APIs para gerenciamento dos usuários cadastrados")
@RestController
@RequestMapping("/users")
public class UserController {

    private final GetUserInputPort getUserInputPort;

    private final UpdateCurrentUserInputPort updateCurrentUserInputPort;

    public UserController(GetUserInputPort getUserInputPort, UpdateCurrentUserInputPort updateCurrentUserInputPort) {
        this.getUserInputPort = getUserInputPort;
        this.updateCurrentUserInputPort = updateCurrentUserInputPort;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna os dados de um usuário através do id")
    public ResponseEntity<UserResponse> get(@PathVariable(name = "id") String id) {
        var userDTO = getUserInputPort.getById(id);
        return ResponseEntity.ok(new UserResponse(userDTO));
    }

    @PatchMapping
    @Operation(summary = "Atualiza os dados do usuário logado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserResponse> update(
            @Valid @RequestBody UpdateUserRequest request,
            @Parameter(hidden = true)  @RequestHeader(name = "Authorization") String token
    ) {
        var userDTO = updateCurrentUserInputPort.update(token, request.toUpdateUserDTO());
        return ResponseEntity.ok(new UserResponse(userDTO));
    }
}
