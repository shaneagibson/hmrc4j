package uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model;

import uk.co.epsilontechnologies.hmrc4j.core.model.Money;

public class PensionsAnnuitiesAndOtherStateBenefitsTax {

    private final Money otherPensionsAndRetirementAnnuities;
    private final Money incapacityBenefit;

    public PensionsAnnuitiesAndOtherStateBenefitsTax(
            final Money otherPensionsAndRetirementAnnuities,
            final Money incapacityBenefit) {
        this.otherPensionsAndRetirementAnnuities = otherPensionsAndRetirementAnnuities;
        this.incapacityBenefit = incapacityBenefit;
    }

    public Money getOtherPensionsAndRetirementAnnuities() {
        return otherPensionsAndRetirementAnnuities;
    }

    public Money getIncapacityBenefit() {
        return incapacityBenefit;
    }

}
