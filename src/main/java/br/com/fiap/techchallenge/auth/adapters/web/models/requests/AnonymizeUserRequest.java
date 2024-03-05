package br.com.fiap.techchallenge.auth.adapters.web.models.requests;

import br.com.fiap.techchallenge.auth.core.dtos.CreateAnonymizeRequestDTO;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AnonymizeUserRequest {
    private String name;
    private String phone;
    private String address;

    public AnonymizeUserRequest() {
    }

    public AnonymizeUserRequest(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    @NotBlank(message = "name não pode ser vazio")
    @NotNull(message = "name não pode ser nulo")
    public String getName() {
        return name;
    }



    @Digits(integer = 11, fraction = 0, message = "Phone deve conter 11 digitos")
    @NotNull(message = "phone não pode ser nulo")
    public String getPhone() {
        return phone;
    }

    @NotBlank(message = "Address não pode ser vazio")
    @NotNull(message = "Address não pode ser nulo")
    public String getAddress() {
        return address;
    }

    public CreateAnonymizeRequestDTO toCreateAnonymizeRequestDTO() {
        return new CreateAnonymizeRequestDTO(
            this.name,
            this.phone,
            this.address
        );
    }
}
