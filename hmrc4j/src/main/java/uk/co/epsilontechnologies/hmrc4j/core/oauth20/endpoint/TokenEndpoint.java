package uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;

import java.time.Instant;

/**
 * A wrapper for the HMRC OAuth 2.0 Token Endpoint.
 */
public class TokenEndpoint {

    /**
     * The Logger for this class
     */
    private static final Log LOG = LogFactory.getLog(TokenEndpoint.class);

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
     * @param credentials the HMRC Credentials for the client.
     */
    public TokenEndpoint(final String baseUrl, final HmrcCredentials credentials) {
        this.baseUrl = baseUrl;
        this.credentials = credentials;
    }

    /**
     * Refreshes the Authority for the given Refresh Token.
     * @param refreshToken the refresh token for the authority being refreshed
     * @param redirectUri the redirect URI for which the authority was originally requested
     * @return the new token
     */
    public Token refresh(final String refreshToken, final String redirectUri) {
        return requestToken("refresh_token", "refresh_token", refreshToken, redirectUri);
    }

    /**
     * Exchanges the given Authorization Code for an OAuth 2.0 token
     * @param authorizationCode the authorization code to exchange
     * @param redirectUri the redirect URI for which the authority was originally requested
     * @return the token
     */
    public Token exchange(final String authorizationCode, final String redirectUri) {
        return requestToken("authorization_code", "code", authorizationCode, redirectUri);
    }

    /**
     * Requests a token for the given grant type
     * @param grantType the grant type (either "refresh_token" or "authorization_code")
     * @param key the key for the request (either "refresh_token" or "code")
     * @param value the value for the request (either the value of the refresh_token or the authorization_code)
     * @param redirectUri the redirect URI for which the authority was originally requested
     * @return the token
     */
    private Token requestToken(final String grantType, final String key, final String value, final String redirectUri) {
        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest.post(baseUrl + "/oauth/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .field("grant_type", grantType)
                    .field(key, value)
                    .field("redirect_uri", redirectUri)
                    .field("client_id", this.credentials.getClientId())
                    .field("client_secret", this.credentials.getClientSecret())
                    .asJson();
            switch (jsonResponse.getStatus()) {
                case 200 : return asToken(jsonResponse.getBody().getObject());
                default : {
                    LOG.error(String.format("invalid response: %s %s %s",
                            jsonResponse.getStatus(),
                            jsonResponse.getStatusText(),
                            jsonResponse.getBody().toString()));
                    throw new RuntimeException("unable to exchange code for token");
                }
            }
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts the given JSONObject into a token
     * @param json the json to convert
     * @return the token
     */
    private Token asToken(final JSONObject json) {
        return new Token(
                json.getString("access_token"),
                json.getString("refresh_token"),
                json.getInt("expires_in"),
                json.getString("token_type"),
                Scope.asList(json.has("scope") ? json.getString("scope") : ""), // NOTE - scope is not provided when refreshing
                Instant.now());
    }

}