package uk.co.epsilontechnologies.hmrc4j.core.model.error;

/**
 * An attempt was made to fetch a required OAuth Token from the Token Store, but no such token existed.
 */
public class TokenNotFoundException extends RuntimeException {

    /**
     * Default Constructor
     */
    public TokenNotFoundException() {
        super("token not found");
    }

}