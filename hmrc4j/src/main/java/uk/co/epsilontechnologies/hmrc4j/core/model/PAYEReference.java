package uk.co.epsilontechnologies.hmrc4j.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static uk.co.epsilontechnologies.hmrc4j.core.util.Require.require;

public class PAYEReference {

    private final String value;

    public PAYEReference(final String value) {
        require(isValid(value), "PAYEReference is not valid");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

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
