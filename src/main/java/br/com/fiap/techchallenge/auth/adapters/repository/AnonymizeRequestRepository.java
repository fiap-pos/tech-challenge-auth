package br.com.fiap.techchallenge.auth.adapters.repository;

import br.com.fiap.techchallenge.auth.adapters.repository.mappers.AnonymizeRequestMapper;
import br.com.fiap.techchallenge.auth.adapters.repository.mongo.AnonymizeRequestMongoRepository;
import br.com.fiap.techchallenge.auth.core.dtos.AnonymizeRequestDTO;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateAnonymizeRequestOutputPort;
import org.springframework.stereotype.Repository;

@Repository
public class AnonymizeRequestRepository implements CreateAnonymizeRequestOutputPort {

    private final AnonymizeRequestMongoRepository anonymizeRequestMongoRepository;

    private final AnonymizeRequestMapper anonymizeRequestMapper;

    public AnonymizeRequestRepository(
            AnonymizeRequestMongoRepository anonymizeRequestMongoRepository,
            AnonymizeRequestMapper anonymizeRequestMapper
    ) {
        this.anonymizeRequestMongoRepository = anonymizeRequestMongoRepository;
        this.anonymizeRequestMapper = anonymizeRequestMapper;
    }

    @Override
    public AnonymizeRequestDTO create(AnonymizeRequestDTO createAnonymizeRequestDTO) {
        var anonymizeRequest = anonymizeRequestMapper.toEntity(createAnonymizeRequestDTO);
        var anonymizeRequestSaved = anonymizeRequestMongoRepository.save(anonymizeRequest);
        return anonymizeRequestMapper.toDTO(anonymizeRequestSaved);
    }
}
