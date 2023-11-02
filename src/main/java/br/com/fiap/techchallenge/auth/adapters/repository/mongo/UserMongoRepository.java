package br.com.fiap.techchallenge.auth.adapters.repository.mongo;

import br.com.fiap.techchallenge.auth.adapters.repository.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserMongoRepository extends MongoRepository<User, String> {
    public Optional<User> findByUsername(String username);
}
