package uk.co.epsilontechnologies.hmrc4j.core.oauth20;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Token {

    private static final long EXPIRY_SKEW_IN_SECONDS = 120;

    private final String accessToken;
    private final String refreshToken;
    private final int expiresIn;
    private final String tokenType;
    private final List<Scope> scope;
    private final Instant createdAt;

    public Token(
            final String accessToken,
            final String refreshToken,
            final int expiresIn,
            final String tokenType,
            final List<Scope> scope,
            final Instant createdAt) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.tokenType = tokenType;
        this.scope = scope;
        this.createdAt = createdAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public List<Scope> getScope() {
        return scope;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(createdAt.plus(expiresIn - EXPIRY_SKEW_IN_SECONDS, ChronoUnit.SECONDS));
    }

}