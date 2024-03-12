package br.com.fiap.techchallenge.auth.adapters.repository.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "anonymize_requests")
public class AnonymizeRequest {

    @Id
    private String id;

    private String name;

    private String phone;

    private String address;

    private LocalDateTime createdAt;

    public AnonymizeRequest(String name, String phone, String address, LocalDateTime createdAt) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
