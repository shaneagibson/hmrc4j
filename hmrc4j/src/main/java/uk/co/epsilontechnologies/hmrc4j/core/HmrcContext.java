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

    private final String baseUrl;

    /**
     * Instantiate a HMRC Context with empty Token Manager and Credentials.
     *
     * @param baseUrl the Base URL for the HMRC API Gateway
     */
    HmrcContext(final String baseUrl) {
        this(baseUrl, Optional.empty(), Optional.empty());
    }

    /**
     * Instantiate a HMRC Context with the given Credentials and an empty Token Manager.
     *
     * @param baseUrl the Base URL for the HMRC API Gateway
     * @param credentials the HMRC Client Application Credentials
     */
    HmrcContext(
            final String baseUrl,
            final HmrcCredentials credentials) {
        this(baseUrl, Optional.of(credentials), Optional.empty());
    }

    /**
     * Instantiate a HMRC Context with the given Credentials and Token Manager.
     *
     * @param baseUrl the Base URL for the HMRC API Gateway
     * @param credentials the HMRC Client Application Credentials
     * @param tokenStore the Token Store for persisting this user's OAuth 2.0 token
     */
    HmrcContext(
            final String baseUrl,
            final HmrcCredentials credentials,
            final TokenStore tokenStore) {
        this(
                baseUrl,
                Optional.of(credentials),
                Optional.of(new TokenManager(
                    new AuthorizeEndpoint(baseUrl, credentials),
                    new TokenEndpoint(baseUrl, credentials),
                    new RevokeEndpoint(baseUrl, credentials),
                    tokenStore)));
    }

    /**
     * Private Constructor.
     *
     * @param baseUrl the Base URL for the HMRC API Gateway
     * @param credentials the HMRC Client Application Credentials
     * @param tokenManager the Token Manager for accessing and maintaining tokens
     */
    private HmrcContext(
            final String baseUrl,
            final Optional<HmrcCredentials> credentials,
            final Optional<TokenManager> tokenManager) {
        this.baseUrl = baseUrl;
        this.tokenManager = tokenManager;
        this.credentials = credentials;
    }

    /**
     * Fetches the base url for the HMRC APIs.
     * @return the base url
     */
    public String getBaseUrl() {
        return this.baseUrl;
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