package br.com.fiap.techchallenge.auth.core.dtos;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;

import java.util.List;

public record UserDTO(String id, String name, String username, String email, Boolean active, List<UserRole> roles) {
    public UserDTO( String name, String username, String email, Boolean active,  List<UserRole> roles) {
        this(null, name, username, email, active, roles);
    }

}
