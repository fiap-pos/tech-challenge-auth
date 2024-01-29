package br.com.fiap.techchallenge.auth.adapters.web.models.requests;

import br.com.fiap.techchallenge.auth.core.dtos.UpdateUserDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class UpdateUserRequest {
    private String name;
    private String email;

    @NotNull(message = "O campo 'name' é obrigatório")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "O campo 'email' é obrigatório")
    @Email(message = "O campos 'email' é inválido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UpdateUserDTO toUpdateUserDTO() {
        return new UpdateUserDTO(
                this.name,
                this.email
        );
    }
}
