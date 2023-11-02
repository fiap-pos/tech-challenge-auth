package br.com.fiap.techchallenge.auth.adapters.web.models.responses;

public class TokenResponse {

    private String type;

    private String payload;

    public TokenResponse(String type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }
}
