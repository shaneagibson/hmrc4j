package uk.co.epsilontechnologies.hmrc4j.core;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;

/**
 * The access point for all of HMRC's APIs.
 */
public class HmrcImpl implements Hmrc {

    private final HmrcContext hmrcContext;

    /**
     * Create a HMRC instance to be used for Unrestricted endpoints only.
     *
     * @param baseUrl the Base URL for the HMRC API Gateway
     */
    public HmrcImpl(final String baseUrl) {
        this.hmrcContext = new HmrcContext(baseUrl);
    }

    /**
     * Create a HMRC instance to be used for Unrestricted or Application-restricted endpoints only.
     *
     * @param baseUrl the Base URL for the HMRC API Gateway
     * @param hmrcCredentials the application's credentials, containing {@code client_id}, {@code client_secret} and {@code server_token}
     */
    public HmrcImpl(final String baseUrl, final HmrcCredentials hmrcCredentials) {
        this.hmrcContext = new HmrcContext(baseUrl, hmrcCredentials);
    }

    /**
     * Create a HMRC instance to be used for Unrestricted, Application-restricted or User-restricted endpoints.
     *
     * @param baseUrl the Base URL for the HMRC API Gateway
     * @param hmrcCredentials the application's credentials, containing {@code client_id}, {@code client_secret} and {@code server_token}
     * @param tokenStore the store for the user's OAuth 2.0 token
     */
    public HmrcImpl(final String baseUrl, final HmrcCredentials hmrcCredentials, final TokenStore tokenStore) {
        this.hmrcContext = new HmrcContext(baseUrl, hmrcCredentials, tokenStore);
    }

    /**
     * Instantiates the given {@code API Class}
     *
     * @param apiClass the {@code Class} to instantiate
     * @param <T> the type of the API
     * @return the instance of the API
     */
    @Override
    public <T extends API> T getAPI(final Class<T> apiClass) {
        try {
            return apiClass.getConstructor(HmrcContext.class).newInstance(this.hmrcContext);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets a URL for the OAuth 2.0 Authorize endpoint, for the given scopes.
     *
     * @param scopes the scopes to authorize
     * @return the authorize URL
     */
    @Override
    public Optional<URL> getAuthorizeUrl(final Scope... scopes) {
        return this.hmrcContext.getTokenManager().map(tokenManager -> tokenManager.getAuthorizeUrl(Arrays.asList(scopes)));
    }

    /**
     * Revokes the OAuth 2.0 Token.
     *
     * @throws IllegalStateException if the context doesn't contain a token manager
     */
    @Override
    public void revokeAuthority() {
        this.hmrcContext.getTokenManager().orElseThrow(() -> new IllegalStateException("no token manager found")).revokeToken();
    }

    /**
     * Exchanges the Authorization Code for an OAuth 2.0 Token, using the token endpoint.
     * The token is persisted via the TokenStore implementation.
     *
     * @param authorizationCode the code to exchange
     */
    @Override
    public void exchange(final String authorizationCode) {
        this.hmrcContext.getTokenManager().orElseThrow(() -> new IllegalStateException("no token manager found")).exchange(authorizationCode);
    }

}