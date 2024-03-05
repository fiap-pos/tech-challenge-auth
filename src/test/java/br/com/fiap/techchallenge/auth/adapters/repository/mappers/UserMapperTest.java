package br.com.fiap.techchallenge.auth.adapters.repository.mappers;

import br.com.fiap.techchallenge.auth.adapters.repository.models.User;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {


    @Test
    void testToUser() {
        UserMapper userMapper = new UserMapper();
        UserDTO dto = new UserDTO("username", "name", "email", true, List.of(UserRole.CUSTOMER));
        User result = userMapper.toUser(dto);


        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(dto.username());
        assertThat(result.getName()).isEqualTo(dto.name());
        assertThat(result.getEmail()).isEqualTo(dto.email());
        assertThat(result.getActive()).isEqualTo(dto.active());
        assertThat(result.getRoles()).isEqualTo(dto.roles());
    }

    @Test
    void testToUserDTO() {
        UserMapper userMapper = new UserMapper();
        User user = new User("username", "name", "email", true, List.of(UserRole.CUSTOMER));
        UserDTO result = userMapper.toUserDTO(user);

        assertThat(result).isNotNull();
        assertThat(result.id()).isEqualTo(user.getId());
        assertThat(result.name()).isEqualTo(user.getName());
        assertThat(result.username()).isEqualTo(user.getUsername());
        assertThat(result.email()).isEqualTo(user.getEmail());
        assertThat(result.active()).isEqualTo(user.getActive());
        assertThat(result.roles()).isEqualTo(user.getRoles());
    }


    @Test
    void shouldReturnUserActiveAsTrueWhenItIsNull() {
        UserMapper userMapper = new UserMapper();
        User user = new User("username", "name", "email", null, List.of(UserRole.CUSTOMER));
        UserDTO result = userMapper.toUserDTO(user);

        assertThat(result.active()).isTrue();
    }

}