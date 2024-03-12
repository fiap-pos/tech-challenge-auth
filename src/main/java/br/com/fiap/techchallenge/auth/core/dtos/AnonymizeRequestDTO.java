package br.com.fiap.techchallenge.auth.core.dtos;

import java.time.LocalDateTime;

public record AnonymizeRequestDTO(String id, String name, String phone, String address, LocalDateTime createdAt){
    public AnonymizeRequestDTO(String name, String phone, String address, LocalDateTime createdAt) {
        this(null, name, phone, address, createdAt);
    }
}
