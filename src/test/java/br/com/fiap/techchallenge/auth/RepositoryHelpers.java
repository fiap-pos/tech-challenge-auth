package br.com.fiap.techchallenge.auth;

import br.com.fiap.techchallenge.auth.adapters.repository.models.User;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;

import java.util.List;

public class RepositoryHelpers {
    public static User getMongoUser() {
        return new User(
            "user-name",
            "user-username",
            "user-email",
            List.of(UserRole.CUSTOMER)
        );
    }
}
