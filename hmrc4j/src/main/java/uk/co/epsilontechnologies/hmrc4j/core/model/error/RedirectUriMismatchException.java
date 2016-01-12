package uk.co.epsilontechnologies.hmrc4j.core.model.error;

/**
 * The Redirect URI provided in a Token endpoint request does not match the original Redirect URI that was provided
 * to the Authorize Endpoint.
 */
public class RedirectUriMismatchException extends Exception {

    /**
     * Default Constructor
     */
    RedirectUriMismatchException() {
        super("redirect uri mismatch");
    }

}
