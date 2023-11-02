package br.com.fiap.techchallenge.auth.adapters.web;

import br.com.fiap.techchallenge.auth.adapters.web.models.responses.UserResponse;
import br.com.fiap.techchallenge.auth.core.domain.entities.User;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.in.GetUserInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Users", description = "APIs para gerenciamento dos usuários cadastrados")
@RestController
@RequestMapping("/users")
public class UserController {

    private final GetUserInputPort getUserInputPort;

    public UserController(GetUserInputPort getUserInputPort) {
        this.getUserInputPort = getUserInputPort;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna os dados de um usuário através do id")
    public ResponseEntity<UserResponse> get(@PathVariable(name = "id") String id) {
        var userDTO = getUserInputPort.getById(id);
        return ResponseEntity.ok(new UserResponse(userDTO));
    }
}
