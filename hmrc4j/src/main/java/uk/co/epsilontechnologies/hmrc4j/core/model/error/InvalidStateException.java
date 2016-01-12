package uk.co.epsilontechnologies.hmrc4j.core.model.error;

/**
 * The state provided in the Redirect did does not match the state that was issued in the original Authorize request.
 * This indicates that the Redirect URI is being invoked in error (possibly by an Cross Site Request Forgery attack).
 */
public class InvalidStateException extends RuntimeException {

    /**
     * Default Constructor
     */
    public InvalidStateException() {
        super("invalid state");
    }

}
