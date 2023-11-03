package br.com.fiap.techchallenge.auth.core.domain.entities;

import br.com.fiap.techchallenge.auth.core.domain.entities.enums.TokenType;
import br.com.fiap.techchallenge.auth.core.domain.entities.enums.UserRole;
import br.com.fiap.techchallenge.auth.core.domain.exceptions.AuthenticationTokenInvalidException;
import br.com.fiap.techchallenge.auth.core.dtos.AuthTokenDTO;
import br.com.fiap.techchallenge.auth.core.dtos.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.ZonedDateTime;
import java.time.Instant;
import java.time.ZoneId;
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

    public UserDTO getUser(String token) {
        try {
            var verifier = JWT.require(getAlgorithm())
                    .withIssuer(ISSUER)
                    .build();
            var decodedJWT = verifier.verify(token.replace("Bearer ", ""));
            return buildUserDTOFromJWT(decodedJWT);
        } catch (JWTVerificationException exception){
            throw new AuthenticationTokenInvalidException("Authorization token is inválid: " + exception.getMessage(), exception);
        }
    }

    private UserDTO buildUserDTOFromJWT(DecodedJWT jwt) {
        var roles = jwt.getClaim("roles")
                .asList(String.class)
                .stream()
                .map(UserRole::valueOf)
                .toList();

        return new UserDTO(
                getUserId(jwt),
                null,
                null,
                null,
                roles
        );
    }

    private String getUserId(DecodedJWT jtw) {
        var claims = jtw.getClaims();
        if (claims.containsKey("id") && claims.get("id") != null) {
            return claims.get("id").asString();
        }
        return null;
    }

    private Instant getCurrentDate() {
        return ZonedDateTime.now(ZoneId.of("UTC")).toInstant();
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    private Long getTokenDurationInSeconds() { return TOKEN_DURATION_MINUTES * 60L; }
}
