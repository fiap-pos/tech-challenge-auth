package br.com.fiap.techchallenge.auth.adapters.repository.mappers;

import br.com.fiap.techchallenge.auth.adapters.repository.models.AnonymizeRequest;
import br.com.fiap.techchallenge.auth.core.dtos.AnonymizeRequestDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnonymizeRequestMapperTest {

    @Test
    public void testToDTO() {
        var anonymizeRequestMapper = new AnonymizeRequestMapper();
        var anonymizeRequest = new AnonymizeRequest("name", "phone", "address", LocalDateTime.now());
        var result = anonymizeRequestMapper.toDTO(anonymizeRequest);
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(anonymizeRequest.getName());
        assertThat(result.phone()).isEqualTo(anonymizeRequest.getPhone());
        assertThat(result.address()).isEqualTo(anonymizeRequest.getAddress());
        assertThat(result.createdAt()).isEqualTo(anonymizeRequest.getCreatedAt());
    }

    @Test
    public void testToEntity() {
        var anonymizeRequestMapper = new AnonymizeRequestMapper();
        var anonymizeRequestDTO = new AnonymizeRequestDTO("name", "phone", "address", LocalDateTime.now());
        var result = anonymizeRequestMapper.toEntity(anonymizeRequestDTO);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(anonymizeRequestDTO.name());
        assertThat(result.getPhone()).isEqualTo(anonymizeRequestDTO.phone());
        assertThat(result.getAddress()).isEqualTo(anonymizeRequestDTO.address());
        assertThat(result.getCreatedAt()).isEqualTo(anonymizeRequestDTO.createdAt());
    }
}