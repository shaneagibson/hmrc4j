package uk.co.epsilontechnologies.hmrc4j.core.util;

public class Require {

    public static void require(final boolean valid) {
        if (!valid) throw new IllegalStateException();
    }

    public static void require(final boolean valid, final String message) {
        if (!valid) throw new IllegalStateException(message);
    }

}
