package uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;

public class RevokeEndpoint {

    private static final String REVOKE_URL = API.BASE_URL + "/oauth/revoke";

    private static final Log LOG = LogFactory.getLog(RevokeEndpoint.class);

    private final HmrcCredentials credentials;

    public RevokeEndpoint(final HmrcCredentials credentials) {
        this.credentials = credentials;
    }

    public void revoke(final String token) {
        if (true) {
            LOG.warn("!!! REVOKE endpoint is not yet implemented by HMRC !!!");
            return; // TODO - REVOKE endpoint is not yet implemented by HMRC
        }
        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest.post(REVOKE_URL)
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