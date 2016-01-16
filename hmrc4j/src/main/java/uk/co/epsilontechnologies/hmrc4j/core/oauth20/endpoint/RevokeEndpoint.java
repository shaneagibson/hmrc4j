package uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;

/**
 * A wrapper for the HMRC OAuth 2.0 Revoke Endpoint.
 */
public class RevokeEndpoint {

    /**
     * The Logger for this class
     */
    private static final Log LOG = LogFactory.getLog(RevokeEndpoint.class);

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
    public RevokeEndpoint(final String baseUrl, final HmrcCredentials credentials) {
        this.baseUrl = baseUrl;
        this.credentials = credentials;
    }

    /**
     * Revokes the given OAuth 2.0 Token from HMRC's API Gateway.
     * @param token the token to revoke
     */
    public void revoke(final String token) {
        if (true) {
            LOG.warn("!!! REVOKE endpoint is not yet implemented by HMRC !!!");
            return; // TODO - REVOKE endpoint is not yet implemented by HMRC
        }
        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest.post(baseUrl + "/oauth/revoke")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json")
                    .field("client_id", this.credentials.getClientId())
                    .field("client_secret", this.credentials.getClientSecret())
                    .field("token", token)
                    .asJson();
            switch (jsonResponse.getStatus()) {
                case 201 : return;
                default : throw new RuntimeException("unable to revoke token");
            }
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

}