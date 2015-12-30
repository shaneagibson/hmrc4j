package uk.co.epsilontechnologies.hmrc4j.core;

/**
 * The credentials for a HMRC Client Application.
 */
public class HmrcCredentials {

    /**
     * The unique identifier for the client application
     */
    private final String clientId;

    /**
     * The secret for the client application
     */
    private final String clientSecret;

    /**
     * The server token for the client application. This allows the application to access Application-restricted endpoints.
     */
    private final String serverToken;

    /**
     * Instantiates a container for HMRC Credentials.
     *
     * @param clientId the unique identifier for the client application
     * @param clientSecret the secret for the client application
     * @param serverToken the server token for the client application
     */
    public HmrcCredentials(
            final String clientId,
            final String clientSecret,
            final String serverToken) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.serverToken = serverToken;
    }

    /**
     * Fetches the unique identifier for the client application.
     * @return the client identifier
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Fetches the secret for the client application.
     * @return the client secret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Fetches the server token for the client application.
     * @return the server token
     */
    public String getServerToken() {
        return serverToken;
    }

}
