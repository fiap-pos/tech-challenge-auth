package br.com.fiap.techchallenge.auth.adapters.repository;

import br.com.fiap.techchallenge.auth.adapters.repository.mappers.AnonymizeRequestMapper;
import br.com.fiap.techchallenge.auth.adapters.repository.models.AnonymizeRequest;
import br.com.fiap.techchallenge.auth.adapters.repository.mongo.AnonymizeRequestMongoRepository;
import br.com.fiap.techchallenge.auth.core.dtos.AnonymizeRequestDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

class AnonymizeRequestRepositoryTest {

    AutoCloseable mock;

    private AnonymizeRequestRepository anonymizeRequestRepository;

    @Mock
    private AnonymizeRequestMongoRepository anonymizeRequestMongoRepository;

    @Mock
    private AnonymizeRequestMapper anonymizeRequestMapper;

    @BeforeEach
    void setUp() {
        mock = MockitoAnnotations.openMocks(this);
        anonymizeRequestRepository = new AnonymizeRequestRepository(
                anonymizeRequestMongoRepository,
                anonymizeRequestMapper
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void shouldCreateAnonymizeRequest() {
        var anonymizeRequestDTO = new AnonymizeRequestDTO(
                "name",
                "11999999999",
                "User full address",
                LocalDateTime.now()
        );

        var anonymizeRequest = new AnonymizeRequest("name", "phone", "address", LocalDateTime.now());

        when(anonymizeRequestMapper.toEntity(anonymizeRequestDTO)).thenReturn(anonymizeRequest);
        when(anonymizeRequestMongoRepository.save(anonymizeRequest)).thenReturn(anonymizeRequest);
        when(anonymizeRequestMapper.toDTO(anonymizeRequest)).thenReturn(anonymizeRequestDTO);

        var createdAnonymizeRequest = anonymizeRequestRepository.create(anonymizeRequestDTO);

        assertThat(createdAnonymizeRequest).isNotNull().isEqualTo(anonymizeRequestDTO);
    }
}