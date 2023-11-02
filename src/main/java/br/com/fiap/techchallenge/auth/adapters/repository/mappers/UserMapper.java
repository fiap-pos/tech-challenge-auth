package br.com.fiap.techchallenge.auth.adapters.repository.mappers;

import br.com.fiap.techchallenge.auth.adapters.repository.models.User;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toUser(UserDTO dto) {
        return new User(
            dto.username(),
            dto.name(),
            dto.email(),
            dto.roles()
        );
    }

    public UserDTO toUserDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.getRoles()
        );
    }
}
