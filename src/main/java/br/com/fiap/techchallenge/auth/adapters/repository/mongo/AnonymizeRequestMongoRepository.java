package br.com.fiap.techchallenge.auth.adapters.repository.mongo;

import br.com.fiap.techchallenge.auth.adapters.repository.models.AnonymizeRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnonymizeRequestMongoRepository extends MongoRepository<AnonymizeRequest, String> {
}
