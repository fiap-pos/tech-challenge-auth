package br.com.fiap.techchallenge.auth.adapters.repository.models;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String name;

    private String email;

    @Indexed
    private Boolean active;

    @Indexed
    private List<UserRole> roles;

    public User(String username, String name, String email, Boolean active, List<UserRole> roles) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.active = active;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getActive() {
        if (active == null) {
            return true;
        }
        return active;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
