package br.com.fiap.techchallenge.auth.adapters.repository;

import br.com.fiap.techchallenge.auth.adapters.repository.mappers.UserMapper;
import br.com.fiap.techchallenge.auth.adapters.repository.mongo.UserMongoRepository;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements CreateUserOutputPort, GetUserOutputPort {

    private UserMongoRepository userMongoRepository;

    private UserMapper userMapper;

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
        var user = userMongoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário com id "+id+" não encontrado."));
        return userMapper.toUserDTO(user);
    }

    @Override
    public UserDTO getByUsername(String username) {
        var user = userMongoRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Usuário com username "+username+" não encontrado."));
        return userMapper.toUserDTO(user);
    }
}
