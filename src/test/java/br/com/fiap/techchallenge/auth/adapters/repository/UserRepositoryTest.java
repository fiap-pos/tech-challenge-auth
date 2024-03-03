package br.com.fiap.techchallenge.auth.adapters.repository;

import br.com.fiap.techchallenge.auth.adapters.repository.mappers.UserMapper;
import br.com.fiap.techchallenge.auth.adapters.repository.mongo.UserMongoRepository;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.auth.core.dtos.UpdateUserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static br.com.fiap.techchallenge.auth.Helpers.getUserDTO;
import static br.com.fiap.techchallenge.auth.RepositoryHelpers.getMongoUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserRepositoryTest {

    AutoCloseable mock;

    private UserRepository userRepository;

    @Mock
    private UserMongoRepository userMongoRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        userRepository = new UserRepository(
                userMongoRepository,
                userMapper
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldGetUserByUsername() {
        var user = getMongoUser();
        var userDTO = getUserDTO();

        when(userMongoRepository.findByUsername(userDTO.username())).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        var foundUser = userRepository.getByUsername(userDTO.username());

        assertThat(foundUser).isNotNull().isEqualTo(userDTO);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUserNotFound() {
        when(userMongoRepository.findByUsername("username")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userRepository.getByUsername("username"));
    }

    @Test
    void shouldGetUserById() {
        var user = getMongoUser();
        var userDTO = getUserDTO();

        when(userMongoRepository.findById(userDTO.id())).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        var foundUser = userRepository.getById(userDTO.id());

        assertThat(foundUser).isNotNull().isEqualTo(userDTO);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenUserNotFoundById() {
        when(userMongoRepository.findById("user-id")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userRepository.getById("user-id"));
    }

    @Test
    void shouldCreateUser() {
        var user = getMongoUser();
        var userDTO = getUserDTO();

        when(userMapper.toUser(userDTO)).thenReturn(user);
        when(userMongoRepository.save(user)).thenReturn(user);
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);

        var createdUser = userRepository.create(userDTO);

        assertThat(createdUser).isNotNull().isEqualTo(userDTO);
    }

    @Test
    void shouldUpdateUser() {
        var user = getMongoUser();
        var userDTO = getUserDTO();
        var updatedUserDTO = new UpdateUserDTO("new name", "new email");


        when(userMongoRepository.findById(userDTO.id())).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(userMongoRepository.save(user)).thenReturn(user);

        var updatedUser = userRepository.update(userDTO.id(), updatedUserDTO);

        assertThat(updatedUser).isNotNull().isEqualTo(userDTO);
    }

    @Test
    void shouldUpdateCompleteUser() {
        var user = getMongoUser();
        var userDTO = getUserDTO();

        when(userMongoRepository.findById(userDTO.id())).thenReturn(Optional.of(user));
        when(userMapper.toUserDTO(user)).thenReturn(userDTO);
        when(userMongoRepository.save(user)).thenReturn(user);

        var updatedUser = userRepository.update(userDTO.id(), userDTO);

        assertThat(updatedUser).isNotNull().isEqualTo(userDTO);
    }


}