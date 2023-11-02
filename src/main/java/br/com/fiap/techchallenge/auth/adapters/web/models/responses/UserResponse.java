package br.com.fiap.techchallenge.auth.adapters.web.models.responses;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

import java.util.List;

public class UserResponse {
    private String id;
    private String name;
    private String username;
    private String email;
    private List<UserRole> roles;

    public UserResponse(UserDTO user) {
        this.id = user.id();
        this.name = user.name();
        this.username = user.username();
        this.email = user.email();
        this.roles = user.roles();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
