package uk.co.epsilontechnologies.hmrc4j.core.model.error;

/**
 * The given UTR is invalid.
 */
public class InvalidUTRException extends Exception {

    /**
     * Default Constructor
     * @param message the reason for the exception
     */
    public InvalidUTRException(final String message) {
        super(message);
    }

}
