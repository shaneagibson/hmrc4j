package uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint;

import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

/**
 * A wrapper for the HMRC OAuth 2.0 Authorize Endpoint.
 */
public class AuthorizeEndpoint {

    /**
     * The Base URL for the HMRC API Gateway (and OAuth 2.0 Service).
     */
    private final String baseUrl;

    /**
     * The HMRC Credentials for the client.
     */
    private final HmrcCredentials credentials;

    /**
     * Default Constructor
     * @param baseUrl the Base URL for the HMRC API Gateway (and OAuth 2.0 Service).
     * @param credentials the HMRC credentials for the client.
     */
    public AuthorizeEndpoint(final String baseUrl, final HmrcCredentials credentials) {
        this.baseUrl = baseUrl;
        this.credentials = credentials;
    }

    /**
     * Returns the URL for the Authorize endpoint of HMRC's OAuth 2.0 Service
     * @param redirectUri the redirect URI for the authorization
     * @param scope the scope for the authorization
     * @param state the optional state (for CSRF protection)
     * @return the URL for the Authorize endpoint
     */
    public URL getURL(final String redirectUri, final List<Scope> scope, final Optional<String> state) {
        try {
            final StringBuilder url = new StringBuilder();
            url.append(baseUrl).append("/oauth/authorize");
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