package br.com.fiap.techchallenge.auth.core.domain.entities;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;

import java.util.List;

public class User {
    private String id;
    private String name;
    private String username;
    private String email;
    private Boolean active;

    private List<UserRole> roles;

    public User(String name, String username, String email, Boolean active, List<UserRole> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    public User(String id, String name, String username, String email, Boolean active, List<UserRole> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public List<UserRole> getRoles() {
        return roles;
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

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isActive() {
        return active;
    }
}
