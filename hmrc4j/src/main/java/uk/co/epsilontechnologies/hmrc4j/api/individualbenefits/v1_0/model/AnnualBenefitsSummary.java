package uk.co.epsilontechnologies.hmrc4j.api.individualbenefits.v1_0.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class AnnualBenefitsSummary {

    private final List<EmploymentBenefits> employments;

    public AnnualBenefitsSummary(final List<EmploymentBenefits> employments) {
        this.employments = employments;
    }

    public List<EmploymentBenefits> getEmployments() {
        return employments;
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
