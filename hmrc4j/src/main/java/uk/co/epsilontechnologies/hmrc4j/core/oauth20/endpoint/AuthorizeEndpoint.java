package uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint;

import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class AuthorizeEndpoint {

    private static final String AUTHORIZE_URL = API.BASE_URL + "/oauth/authorize";

    private final HmrcCredentials credentials;

    public AuthorizeEndpoint(final HmrcCredentials credentials) {
        this.credentials = credentials;
    }

    public URL getURL(final String redirectUri, final List<Scope> scope, final Optional<String> state) {
        try {
            final StringBuilder url = new StringBuilder();
            url.append(AUTHORIZE_URL);
            url.append("?response_type=").append("code");
            url.append("&client_id=").append(credentials.getClientId());
            url.append("&scope=").append(Scope.asString(scope));
            url.append("&redirect_uri=").append(redirectUri);
            if (state.isPresent()) url.append("&state=").append(state.get());
            return new URL(url.toString());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}