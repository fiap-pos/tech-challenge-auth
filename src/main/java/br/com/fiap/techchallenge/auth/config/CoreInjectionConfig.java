package br.com.fiap.techchallenge.auth.config;

import br.com.fiap.techchallenge.auth.core.ports.in.*;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateAnonymizeRequestOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.CreateUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.GetUserOutputPort;
import br.com.fiap.techchallenge.auth.core.ports.out.UpdateUserOutputPort;
import br.com.fiap.techchallenge.auth.core.usecases.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreInjectionConfig {

    @Value("${techchalenge.auth.secret}")
    private String authSecret;

    @Bean
    CreateCustomerUserInputPort createCustomerUser(CreateUserOutputPort createUserOutputPort, GetUserOutputPort getUserOutputPort) {
        return new CreateCustomerUserUseCase(createUserOutputPort, getUserOutputPort);
    }

    @Bean
    GetUserInputPort getUser(GetUserOutputPort getUserOutputPort) {
        return new GetUserUseCase(getUserOutputPort);
    }

    @Bean
    AuthInputPort authenticateUser(GetUserOutputPort getUserOutputPort) {
        return new AuthenticateUseCase(getUserOutputPort, authSecret);
    }

    @Bean
    UpdateCurrentUserInputPort updateUser(UpdateUserOutputPort updateUserOutputPort) {
        return new UpdateCurrentUserUseCase(updateUserOutputPort, authSecret);
    }

    @Bean
    AnonymizeUserInputPort anonimizeData(
            GetUserOutputPort getUserOutputPort,
            UpdateUserOutputPort updateUserOutputPort,
            CreateAnonymizeRequestOutputPort createAnonymizeRequestOutputPort
    ) {
        return new AnonymizeUserUseCase(getUserOutputPort, updateUserOutputPort, createAnonymizeRequestOutputPort);
    }
}
