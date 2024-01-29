package br.com.fiap.techchallenge.auth;

import br.com.fiap.techchallenge.auth.core.domain.entities.User;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

import java.util.List;

public class Helpers {
    public static UserDTO getUserDTO() {
        return new UserDTO(
                "user-id",
                "user-name",
                "user-username",
                "user-email",
                List.of(UserRole.CUSTOMER)
        );
    }

    public static User getUser(String id) {
        return new User(
                id,
                "user-name",
                "user-username",
                "user-email",
                List.of(UserRole.CUSTOMER)
        );
    }
}