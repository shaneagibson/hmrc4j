package uk.co.epsilontechnologies.hmrc4j.core.oauth20;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * A Model Object representing an OAuth 2.0 Token.
 */
public class Token {

    /**
     * The skew applied to timestamp expiry. This is to prevent a token from expiring between the time that the
     * expiration check is performed and when the request is issued.
     */
    private static final long EXPIRY_SKEW_IN_SECONDS = 10;

    /**
     * The OAuth 2.0 Access Token
     */
    private final String accessToken;

    /**
     * The OAuth 2.0 Refresh Token
     */
    private final String refreshToken;

    /**
     * The period of time (in seconds) in which the token will expire
     */
    private final int expiresIn;

    /**
     * The type of token (currently this is always "Bearer")
     */
    private final String tokenType;

    /**
     * The scope of the token
     */
    private final List<Scope> scope;

    /**
     * The timestamp at which the token was created
     */
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

    /**
     * Determines if the token has expired.
     * @return true if the token has expired, otherwise false.
     */
    public boolean isExpired() {
        return Instant.now().isAfter(createdAt.plus(expiresIn - EXPIRY_SKEW_IN_SECONDS, ChronoUnit.SECONDS));
    }

    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}