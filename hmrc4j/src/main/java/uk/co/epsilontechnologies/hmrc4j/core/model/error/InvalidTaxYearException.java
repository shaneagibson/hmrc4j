package uk.co.epsilontechnologies.hmrc4j.core.model.error;

/**
 * The given tax year is invalid.
 */
public class InvalidTaxYearException extends Exception {

    /**
     * Default Constructor
     * @param message the reason for the exception
     */
    public InvalidTaxYearException(final String message) {
        super(message);
    }

}
