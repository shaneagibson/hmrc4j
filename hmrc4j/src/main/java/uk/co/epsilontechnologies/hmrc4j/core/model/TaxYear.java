package uk.co.epsilontechnologies.hmrc4j.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.regex.Pattern;

import static uk.co.epsilontechnologies.hmrc4j.core.util.Require.require;

public class TaxYear {

    private final String value;

    public TaxYear(final String value) {
        require(isValid(value), "TaxYear is not valid");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(final String value) {
        if (value == null) return false;
        if (!Pattern.compile("(\\b19|\\b20)\\d\\d-\\d\\d").matcher(value).matches()) return false;
        final int yy1 = Integer.parseInt(value.substring(2, 4));
        final int yy2 = Integer.parseInt(value.substring(5));
        if (yy1 == 99 && yy2 != 0) return false;
        if (yy1 != 99 && yy1 + 1 != yy2) return false;
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
