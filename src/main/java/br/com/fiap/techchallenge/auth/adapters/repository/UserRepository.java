package br.com.fiap.techchallenge.auth.adapters.repository;

import br.com.fiap.techchallenge.auth.adapters.repository.mappers.UserMapper;
import br.com.fiap.techchallenge.auth.adapters.repository.models.User;
import br.com.fiap.techchallenge.auth.adapters.repository.mongo.UserMongoRepository;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.auth.core.dtos.UpdateUserDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.UpdateUserOutputPort;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements CreateUserOutputPort, GetUserOutputPort, UpdateUserOutputPort {

    private final UserMongoRepository userMongoRepository;

    private final UserMapper userMapper;

    public UserRepository(UserMongoRepository userMongoRepository, UserMapper userMapper) {
        this.userMongoRepository = userMongoRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO create(UserDTO userIn) {
        var user = userMongoRepository.save(userMapper.toUser(userIn));
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO getById(String id) {
        var user = findById(id);
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO getByUsername(String username) {
        var user = userMongoRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Usuário com username "+username+" não encontrado."));
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO update(String id, UpdateUserDTO userDTO) {
        var user = findById(id);

        user.setName(userDTO.name());
        user.setEmail(userDTO.email());

        var updatedUser = userMongoRepository.save(user);

        return userMapper.toUserDTO(updatedUser);
    }

    @Override
    public UserDTO update(String id, UserDTO userDTO) {
        var user = findById(id);

        user.setName(userDTO.name());
        user.setUsername(userDTO.username());
        user.setEmail(userDTO.email());
        user.setActive(userDTO.active());
        user.setRoles(userDTO.roles());

        var updatedUser = userMongoRepository.save(user);

        return userMapper.toUserDTO(updatedUser);
    }

    private User findById(String id) {
        return userMongoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id "+id+" não encontrado."));
    }
}
