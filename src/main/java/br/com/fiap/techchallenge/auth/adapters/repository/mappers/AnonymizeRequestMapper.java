package br.com.fiap.techchallenge.auth.adapters.repository.mappers;

import br.com.fiap.techchallenge.auth.adapters.repository.models.AnonymizeRequest;
import br.com.fiap.techchallenge.auth.core.dtos.AnonymizeRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class AnonymizeRequestMapper {

    public AnonymizeRequestDTO toDTO(AnonymizeRequest anonymizeRequest) {
        return new AnonymizeRequestDTO(
                anonymizeRequest.getId(),
                anonymizeRequest.getName(),
                anonymizeRequest.getPhone(),
                anonymizeRequest.getAddress(),
                anonymizeRequest.getCreatedAt()
        );
    }

    public AnonymizeRequest toEntity(AnonymizeRequestDTO anonymizeRequestDTO) {
        return new AnonymizeRequest(
                anonymizeRequestDTO.name(),
                anonymizeRequestDTO.phone(),
                anonymizeRequestDTO.address(),
                anonymizeRequestDTO.createdAt()
        );
    }
}
