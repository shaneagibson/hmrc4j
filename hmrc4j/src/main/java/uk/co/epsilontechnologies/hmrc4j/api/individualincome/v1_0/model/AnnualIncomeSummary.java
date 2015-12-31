package uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class AnnualIncomeSummary {

    private final PensionsAnnuitiesAndOtherStateBenefitsIncome pensionsAnnuitiesAndOtherStateBenefits;
    private final List<EmploymentIncome> employments;

    public AnnualIncomeSummary(
            final PensionsAnnuitiesAndOtherStateBenefitsIncome pensionsAnnuitiesAndOtherStateBenefits,
            final List<EmploymentIncome> employments) {
        this.pensionsAnnuitiesAndOtherStateBenefits = pensionsAnnuitiesAndOtherStateBenefits;
        this.employments = employments;
    }

    public PensionsAnnuitiesAndOtherStateBenefitsIncome getPensionsAnnuitiesAndOtherStateBenefits() {
        return pensionsAnnuitiesAndOtherStateBenefits;
    }

    public List<EmploymentIncome> getEmployments() {
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
