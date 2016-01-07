package uk.co.epsilontechnologies.hmrc4j.core.oauth20;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.AuthorizeEndpoint;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.RevokeEndpoint;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.TokenEndpoint;

import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * Encapsulates the management of an OAuth 2.0 Token.
 */
public class TokenManager {

    private final AuthorizeEndpoint authorizeEndpoint;
    private final TokenEndpoint tokenEndpoint;
    private final RevokeEndpoint revokeEndpoint;
    private final TokenStore tokenStore;

    /**
     * Constructs a TokenManager or the given endpoints and token store implementation.
     *
     * @param authorizeEndpoint the authorize endpoint
     * @param tokenEndpoint the token endpoint
     * @param revokeEndpoint the revoke endpoint
     * @param tokenStore the token store implementation
     */
    public TokenManager(
            final AuthorizeEndpoint authorizeEndpoint,
            final TokenEndpoint tokenEndpoint,
            final RevokeEndpoint revokeEndpoint,
            final TokenStore tokenStore) {
        this.authorizeEndpoint = authorizeEndpoint;
        this.tokenEndpoint = tokenEndpoint;
        this.revokeEndpoint = revokeEndpoint;
        this.tokenStore = tokenStore;
    }

    /**
     * Retrieves the token from the token store.
     * @return the optional token
     */
    public Optional<Token> getToken() {
        return tokenStore.getToken();
    }

    /**
     * Refreshes the OAuth 2.0 token in the token store and persists the new token in the tokens store.
     */
    public void refreshToken() {
        final Token token = tokenStore.getToken().orElseThrow(() -> new IllegalStateException("no token found in context"));
        final Token newToken = tokenEndpoint.refresh(token.getRefreshToken(), tokenStore.getRedirectUri().orElseThrow(() -> new IllegalStateException("no redirect uri found in context")));
        tokenStore.setToken(new Token(
                newToken.getAccessToken(),
                newToken.getRefreshToken(),
                newToken.getExpiresIn(),
                newToken.getTokenType(),
                token.getScope(), // NOTE - scope is not provided when refreshing
                newToken.getCreatedAt()));
    }

    /**
     * Revokes the current OAuth 2.0 token.
     */
    public void revokeToken() {
        tokenStore.getToken().ifPresent(token -> {
            revokeEndpoint.revoke(token.getAccessToken());
            tokenStore.removeToken();
        });
    }

    /**
     * Retrieves the authorize URL for the given scopes.
     *
     * @param scopes the scopes for which to generate the URL
     * @return the authorize URL
     */
    public URL getAuthorizeUrl(final List<Scope> scopes) {
        return authorizeEndpoint.getURL(
                tokenStore.getRedirectUri().orElseThrow(() -> new IllegalStateException("no redirect uri found in tokenstore")),
                scopes,
                tokenStore.getState());
    }

    /**
     * Exchanges the given Authorization Code for an OAuth 2.0 Token.
     * @param authorizationCode the authorization code to exchange
     */
    public void exchange(final String authorizationCode) {
        tokenStore.setToken(tokenEndpoint.exchange(
                authorizationCode,
                tokenStore.getRedirectUri().orElseThrow(() -> new IllegalStateException("no redirect uri found in tokenstore"))));
    }

}