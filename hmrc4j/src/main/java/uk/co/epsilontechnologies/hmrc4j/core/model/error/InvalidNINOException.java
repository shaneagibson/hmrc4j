package uk.co.epsilontechnologies.hmrc4j.core.model.error;

/**
 * The given National Insurance Number (NINO) is invalid.
 */
public class InvalidNINOException extends Exception {

    /**
     * Default Constructor
     * @param message the reason for the exception
     */
    public InvalidNINOException(final String message) {
        super(message);
    }

}
