package uk.co.epsilontechnologies.hmrc4j.core.oauth20;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.AuthorizeEndpoint;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.RevokeEndpoint;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.TokenEndpoint;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public class TokenManager {

    private final AuthorizeEndpoint authorizeEndpoint;
    private final TokenEndpoint tokenEndpoint;
    private final RevokeEndpoint revokeEndpoint;
    private final TokenStore tokenStore;

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

    public Optional<Token> getToken() {
        return tokenStore.getToken();
    }

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

    public void revokeToken() {
        tokenStore.getToken().ifPresent(token -> {
            revokeEndpoint.revoke(token.getAccessToken());
            tokenStore.removeToken();
        });
    }

    public URL getAuthorizeUrl(final List<Scope> scopes) {
        return authorizeEndpoint.getURL(
                tokenStore.getRedirectUri().orElseThrow(() -> new IllegalStateException("no redirect uri found in tokenstore")),
                scopes,
                tokenStore.getState());
    }

}