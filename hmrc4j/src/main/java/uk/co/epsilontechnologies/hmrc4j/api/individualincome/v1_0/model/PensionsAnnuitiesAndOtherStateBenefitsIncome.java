package uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.co.epsilontechnologies.hmrc4j.core.model.Money;

public class PensionsAnnuitiesAndOtherStateBenefitsIncome {

    private final Money otherPensionsAndRetirementAnnuities;
    private final Money incapacityBenefit;
    private final Money jobseekersAllowance;

    public PensionsAnnuitiesAndOtherStateBenefitsIncome(
            final Money otherPensionsAndRetirementAnnuities,
            final Money incapacityBenefit,
            final Money jobseekersAllowance) {
        this.otherPensionsAndRetirementAnnuities = otherPensionsAndRetirementAnnuities;
        this.incapacityBenefit = incapacityBenefit;
        this.jobseekersAllowance = jobseekersAllowance;
    }

    public Money getOtherPensionsAndRetirementAnnuities() {
        return otherPensionsAndRetirementAnnuities;
    }

    public Money getIncapacityBenefit() {
        return incapacityBenefit;
    }

    public Money getJobseekersAllowance() {
        return jobseekersAllowance;
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
