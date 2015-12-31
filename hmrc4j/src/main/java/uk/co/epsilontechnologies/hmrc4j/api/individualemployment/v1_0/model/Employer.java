package uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.co.epsilontechnologies.hmrc4j.core.model.PAYEReference;

public class Employer {

    private final PAYEReference payeReference;
    private final String name;

    public Employer(final PAYEReference payeReference, final String name) {
        this.payeReference = payeReference;
        this.name = name;
    }

    public PAYEReference getPayeReference() {
        return payeReference;
    }

    public String getName() {
        return name;
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
