package br.com.fiap.techchallenge.auth.core.domain.entities;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test
    void shouldReturnUser() {
        User user = new User("id", "name", "username", "email", List.of(UserRole.GUEST));
        assertEquals("id", user.getId());
        assertEquals("name", user.getName());
        assertEquals("username", user.getUsername());
        assertEquals("email", user.getEmail());
        assertEquals(List.of(UserRole.GUEST), user.getRoles());
    }
}