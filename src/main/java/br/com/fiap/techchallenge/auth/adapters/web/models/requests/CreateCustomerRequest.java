package br.com.fiap.techchallenge.auth.adapters.web.models.requests;

import br.com.fiap.techchallenge.auth.core.dtos.CreateCustomerDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public class CreateCustomerRequest {
    private String name;
    private String cpf;
    private String email;

    @NotNull(message = "O campo 'name' é obrigatório")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull(message = "O campo 'cpf' é obrigatório")
    @CPF(message = "O campo 'cpf' é inválido")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    @NotNull(message = "O campo 'email' é obrigatório")
    @Email(message = "O campos 'email' é inválido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CreateCustomerDTO toCreateCustomerDTO() {
        return new CreateCustomerDTO(
            this.name,
            this.cpf,
            this.email
        );
    }
}
