package uk.co.epsilontechnologies.hmrc4j.core.model.error;

/**
 * The Redirect URI was invoked without a code - the only reason for this is that the user (individual, organisation,
 * or agent) denied the authorization request.
 */
public class UserDeniedAuthorizationException extends Exception {

    /**
     * Default Constructor
     */
    public UserDeniedAuthorizationException() {
        super("user denied authorization");
    }

}
