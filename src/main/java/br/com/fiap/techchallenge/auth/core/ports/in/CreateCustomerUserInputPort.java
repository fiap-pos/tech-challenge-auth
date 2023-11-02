package br.com.fiap.techchallenge.auth.core.ports.in;

import br.com.fiap.techchallenge.auth.core.dtos.CreateCustomerDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;

public interface CreateCustomerUserInputPort {
    public UserDTO create(CreateCustomerDTO customerIn);
}
