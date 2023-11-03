package br.com.fiap.techchallenge.auth.adapters.web;

import br.com.fiap.techchallenge.auth.adapters.web.models.requests.AuthenticateCustomerRequest;
import br.com.fiap.techchallenge.auth.adapters.web.models.responses.TokenResponse;
import br.com.fiap.techchallenge.auth.adapters.web.models.responses.UserResponse;
import br.com.fiap.techchallenge.auth.core.ports.in.AuthInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "APIs para gerenciamento de Autheticação")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthInputPort authInputPort;

    public AuthController(AuthInputPort authInputPort) {
        this.authInputPort = authInputPort;
    }

    @PostMapping("/guest")
    @Operation(summary = "Autentica um usuário como convidado")
    public ResponseEntity<TokenResponse> authenticateGuest() {
        var authTokenDTO = authInputPort.authenticateGuest();
        return ResponseEntity.ok(new TokenResponse(authTokenDTO));
    }

    @PostMapping("/customer")
    @Operation(summary = "Autentica um usuário do tipo customer")
    public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody AuthenticateCustomerRequest request) {
        var authTokenDTO = authInputPort.authenticateCustomer(request.toAuthCustomerDTO());
        return ResponseEntity.ok(new TokenResponse(authTokenDTO));
    }

    @GetMapping("/info")
    @Operation(summary = "Retorna as informações do usuário logado")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserResponse> authenticate(@RequestHeader(name = "Authorization") String token) {
        var userDTO = authInputPort.getUserByToken(token);
        return ResponseEntity.ok(new UserResponse(userDTO));
    }
}
