package uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class EmploymentHistories {

    private final List<Employer> employers;

    public EmploymentHistories(final List<Employer> employers) {
        this.employers = employers;
    }

    public List<Employer> getEmployers() {
        return this.employers;
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
