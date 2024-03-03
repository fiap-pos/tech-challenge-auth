package br.com.fiap.techchallenge.auth.core.ports.out;

import br.com.fiap.techchallenge.auth.core.dtos.AnonymizeRequestDTO;

public interface CreateAnonymizeRequestOutputPort {
    AnonymizeRequestDTO create(AnonymizeRequestDTO anonymizeRequestDTO);
}
