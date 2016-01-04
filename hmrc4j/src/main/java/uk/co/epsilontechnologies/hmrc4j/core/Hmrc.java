package uk.co.epsilontechnologies.hmrc4j.core;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;

import java.net.URL;
import java.util.Optional;

/**
 * The access point for all of HMRC's APIs.
 */
public interface Hmrc {

    /**
     * Creates an instance of the API Class.
     *
     * @param apiClass the API Class to instantiate
     * @param <T> the API type
     * @return the API instance
     */
    <T extends API> T getAPI(Class<T> apiClass);

    /**
     * Returns the URI for the OAuth 2.0 Authorize endpoint
     *
     * @param scopes the scopes to authorize
     * @return the optional URL
     */
    Optional<URL> getAuthorizeUrl(final Scope... scopes);

    /**
     * Revokes the current OAuth 2.0 authority.
     */
    void revokeAuthority();

    void exchange(final String authorizationCode);

}