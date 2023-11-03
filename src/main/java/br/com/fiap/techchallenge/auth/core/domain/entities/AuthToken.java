package br.com.fiap.techchallenge.auth.core.domain.entities;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.TokenType;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.AuthenticationTokenInvalidException;
import br.com.fiap.techchallenge.auth.core.dtos.AuthTokenDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class AuthToken {

    private static final String ISSUER = "Tech Challenge Auth";
    private static final Long TOKEN_DURATION_MINUTES = 30L;
    private final String secret;

    public AuthToken(String secret) {
        this.secret = secret;
    }

    public AuthTokenDTO createGuest() {

        var roles = new ArrayList<UserRole>();
        roles.add(UserRole.GUEST);

        return buildToken(null, roles);
    }

    public AuthTokenDTO create(User user) {
        return buildToken(user.getId(), user.getRoles());
    }

    private AuthTokenDTO buildToken(String id, List<UserRole> roles) {

        var now = getCurrentDate();
        var expiresAt = now.plus(TOKEN_DURATION_MINUTES, ChronoUnit.MINUTES);

        var builder = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .withClaim("roles", roles.stream().map(Enum::name).toList());

        if (id != null) {
            builder.withClaim("id", id);
        }

        var  accessToken = builder.sign(getAlgorithm());

        return new AuthTokenDTO(
                accessToken,
                TokenType.BEARER,
                getTokenDurationInSeconds()
        );
    }

    public String getUserId(String token) {
        try {
            var verifier = JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build();
            var decodedJWT = verifier.verify(token.replace("Bearer ", ""));
            return decodedJWT.getClaim("id").asString();
        } catch (JWTVerificationException exception){
            throw new AuthenticationTokenInvalidException("Authorization token is inválid", exception);
        }
    }

    private Instant getCurrentDate() {
        long timeInSeconds = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        return Instant.ofEpochSecond(timeInSeconds);

    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Long getTokenDurationInSeconds() { return TOKEN_DURATION_MINUTES * 60L; }
}
