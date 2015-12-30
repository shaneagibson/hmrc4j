package uk.co.epsilontechnologies.hmrc4j.sample.auth;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;

import java.util.Optional;

public class InMemoryTokenStore implements TokenStore {

    private Optional<Token> token = Optional.empty();
    private Optional<String> redirectUri = Optional.empty();
    private Optional<String> state = Optional.empty();

    @Override
    public void removeToken() {
        this.token = Optional.empty();
    }

    @Override
    public Optional<Token> getToken() {
        return this.token;
    }

    @Override
    public Optional<String> getRedirectUri() {
        return this.redirectUri;
    }

    @Override
    public void setToken(final Token token) {
        this.token = Optional.of(token);
    }

    @Override
    public Optional<String> getState() {
        return this.state;
    }

    @Override
    public void setState(final String state) {
        this.state = Optional.of(state);
    }

    @Override
    public void setRedirectUri(final String redirectUri) {
        this.redirectUri = Optional.of(redirectUri);
    }

}
