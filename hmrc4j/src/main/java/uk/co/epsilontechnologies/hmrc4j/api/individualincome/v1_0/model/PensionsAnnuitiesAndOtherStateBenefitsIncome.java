package uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model;

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

}
