package br.com.fiap.techchallenge.auth.core.ports.in;

import br.com.fiap.techchallenge.auth.core.domain.exceptions.EntityNotFoundException;
import br.com.fiap.techchallenge.auth.core.dtos.CreateAnonymizeRequestDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface AnonymizeUserInputPort {
    public UserDTO anonymize(String id, CreateAnonymizeRequestDTO anonymizeRequestDTO) throws EntityNotFoundException;
}
