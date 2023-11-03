package br.com.fiap.techchallenge.auth.adapters.web.models.requests;

import br.com.fiap.techchallenge.auth.core.dtos.AuthCustomerDTO;
import jakarta.validation.constraints.NotNull;

public class AuthenticateCustomerRequest {
    private String username;

    @NotNull(message = "O campo username é obrigatório")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AuthCustomerDTO toAuthCustomerDTO() { return new AuthCustomerDTO(this.getUsername()); }
}
