package uk.co.epsilontechnologies.hmrc4j.core.oauth20;

import java.util.Optional;

/**
 * Interface for persisting and managing a token for a given user. This should be implemented by your application
 * according to your application's needs.
 *
 * Some implementations may use a single Token Store instance (i.e. where there is only one user for the application).
 *
 * Other implementations may need to provide a different Token Store instance for each user (i.e. where they support
 * multiple taxpayers or agents simultaneously).
 *
 * Additionally, you may choose to have different OAuth 2.0 tokens for a each individual user (they would need different
 * scopes, otherwise there would be no point).
 */
public interface TokenStore {

    /**
     * Removes the token for this OAuth 2.0 token request.
     */
    void removeToken();

    /**
     * Retrieves the current token for this OAuth 2.0 token request.
     * @return the optional current token
     */
    Optional<Token> getToken();

    /**
     * Retriees the redirect URI for this OAuth 2.0 token request.
     * @return the optional redirect URI
     */
    Optional<String> getRedirectUri();

    /**
     * Sets the token for this OAuth 2.0 token request.
     * @param token the token
     */
    void setToken(Token token);

    /**
     * Retriees the state for this OAuth 2.0 token request.
     * @return the optional state
     */
    Optional<String> getState();

    /**
     * Sets the state for this OAuth 2.0 authorize request.
     * The state is primarily used to prevent Cross Site Request Forgery.
     * @param state the state
     */
    void setState(String state);

    /**
     * Sets the Redirect URI for this token request.
     * @param redirectUri the redirect URI
     */
    void setRedirectUri(String redirectUri);

}