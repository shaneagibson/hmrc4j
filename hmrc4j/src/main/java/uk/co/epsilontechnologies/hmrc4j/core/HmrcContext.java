package uk.co.epsilontechnologies.hmrc4j.core;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenManager;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.AuthorizeEndpoint;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.RevokeEndpoint;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.TokenEndpoint;

import java.util.Optional;

/**
 * The Context of a HMRC API instance. This contains the OAuth 2.0 Token Manager and HMRC Client Application Credentials (both optional).
 */
public class HmrcContext {

    /**
     * The (optional) Token Manager.
     */
    private final Optional<TokenManager> tokenManager;

    /**
     * The (optional) HMRC Client Application Credentials.
     */
    private final Optional<HmrcCredentials> credentials;

    /**
     * Instantiate a HMRC Context with empty Token Manager and Credentials.
     */
    HmrcContext() {
        this.tokenManager = Optional.empty();
        this.credentials = Optional.empty();
    }

    /**
     * Instantiate a HMRC Context with the given Credentials and an empty Token Manager.
     *
     * @param credentials the HMRC Client Application Credentials
     */
    HmrcContext(final HmrcCredentials credentials) {
        this.tokenManager = Optional.empty();
        this.credentials = Optional.of(credentials);
    }

    /**
     * Instantiate a HMRC Context with the given Credentials and Token Manager.
     *
     * @param credentials the HMRC Client Application Credentials
     * @param tokenStore the Token Store for persisting this user's OAuth 2.0 token
     */
    HmrcContext(final HmrcCredentials credentials, final TokenStore tokenStore) {
        this.tokenManager = Optional.of(new TokenManager(
                new AuthorizeEndpoint(credentials),
                new TokenEndpoint(credentials),
                new RevokeEndpoint(credentials),
                tokenStore));
        this.credentials = Optional.of(credentials);
    }

    /**
     * Fetch the (optional) Token Manager.
     * @return the (optional) Token Manager
     */
    public Optional<TokenManager> getTokenManager() {
        return tokenManager;
    }

    /**
     * Fetch the (optional) HMRC Client Application Credentials.
     * @return the (optional) HMRC Client Application Credentials
     */
    public Optional<HmrcCredentials> getCredentials() {
        return credentials;
    }

}