package uk.co.epsilontechnologies.hmrc4j.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static uk.co.epsilontechnologies.hmrc4j.core.util.Require.require;

/**
 * A domain-specific wrapper for a PAYE Reference.
 */
public class PAYEReference {

    /**
     * The value of the PAYE Reference
     */
    private final String value;

    /**
     * Instantiate a PAYE Reference
     * @param value the raw value as a String
     */
    public PAYEReference(final String value) {
        require(isValid(value), "PAYEReference is not valid");
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
     * Validates the given PAYE Reference value
     * @param value the raw value as a String
     * @return true if the value is valid, false otherwise.
     */
    public static boolean isValid(final String value) {
        return true; // TODO
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
