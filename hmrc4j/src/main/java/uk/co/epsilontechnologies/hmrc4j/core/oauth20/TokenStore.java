package uk.co.epsilontechnologies.hmrc4j.core.oauth20;

import java.util.Optional;

public interface TokenStore {

    void removeToken();

    Optional<Token> getToken();

    Optional<String> getRedirectUri();

    void setToken(Token token);

    Optional<String> getState();

    void setState(String state);

    void setRedirectUri(String redirectUri);

}