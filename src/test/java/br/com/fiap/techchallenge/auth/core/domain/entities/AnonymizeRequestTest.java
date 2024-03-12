package br.com.fiap.techchallenge.auth.core.domain.entities;

import br.com.fiap.techchallenge.auth.adapters.repository.models.AnonymizeRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AnonymizeRequestTest {

    @Test
    void shouldReturnAnonymizeRequest() {
        var createdAt = LocalDateTime.now();

        AnonymizeRequest anonymizeRequest = new AnonymizeRequest("username", "11999999999", "User full address", createdAt);
        assertThat(anonymizeRequest.getName()).isEqualTo("username");
        assertThat(anonymizeRequest.getPhone()).isEqualTo("11999999999");
        assertThat(anonymizeRequest.getAddress()).isEqualTo("User full address");
        assertThat(anonymizeRequest.getCreatedAt()).isEqualTo(createdAt);
    }
}
