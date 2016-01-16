package uk.co.epsilontechnologies.hmrc4j.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.regex.Pattern;

import static uk.co.epsilontechnologies.hmrc4j.core.util.Require.require;

/**
 * A domain-specific wrapper for a Universal Tax Reference number.
 */
public class UTR {

    /**
     * The value of the UTR.
     */
    private final String value;

    /**
     * Default Constructor
     * @param value the raw value as a String.
     */
    public UTR(final String value) {
        require(isValid(value), "UTR is not valid");
        this.value = value;
    }

    /**
     * Retrieves the value as a String
     * @return the value as a String
     */
    public String getValue() {
        return value;
    }

    /**
     * Validates the given UTR value
     * @param value the raw value as a String
     * @return true if the value is valid, false otherwise.
     */
    public static boolean isValid(final String value) {
        if (value == null) return false;
        if (!Pattern.compile("[0-9]{10}").matcher(value).matches()) return false;
        return true;
    }

    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
