package uk.co.epsilontechnologies.hmrc4j.core.util;

/**
 * Utility for checking whether the state of a value is valid.
 */
public class Require {

    /**
     * Throws an IllegalArgumentException if the value is false.
     * @param valid the value to check
     */
    public static void require(final boolean valid) {
        if (!valid) throw new IllegalStateException();
    }

    /**
     * Throws an IllegalStateException if the value is false.
     * @param valid the value to check
     * @param message the message to use if the value is false
     */
    public static void require(final boolean valid, final String message) {
        if (!valid) throw new IllegalStateException(message);
    }

}