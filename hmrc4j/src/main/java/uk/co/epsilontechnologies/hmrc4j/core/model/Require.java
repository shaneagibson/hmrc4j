package uk.co.epsilontechnologies.hmrc4j.core.model;

public class Require {

    public static void require(final boolean value) {
        if (value) throw new IllegalStateException();
    }

    public static void require(final boolean value, final String message) {
        if (value) throw new IllegalStateException(message);
    }

}
