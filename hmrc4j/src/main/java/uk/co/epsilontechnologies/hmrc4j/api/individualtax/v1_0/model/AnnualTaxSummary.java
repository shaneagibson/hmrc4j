package uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model;

import java.util.List;

public class AnnualTaxSummary {

    private final PensionsAnnuitiesAndOtherStateBenefitsTax pensionsAnnuitiesAndOtherStateBenefits;
    private final TaxRefunds refunds;
    private final List<EmploymentTax> employments;

    public AnnualTaxSummary(
            final PensionsAnnuitiesAndOtherStateBenefitsTax pensionsAnnuitiesAndOtherStateBenefits,
            final TaxRefunds refunds,
            final List<EmploymentTax> employments) {
        this.pensionsAnnuitiesAndOtherStateBenefits = pensionsAnnuitiesAndOtherStateBenefits;
        this.refunds = refunds;
        this.employments = employments;
    }

    public PensionsAnnuitiesAndOtherStateBenefitsTax getPensionsAnnuitiesAndOtherStateBenefits() {
        return pensionsAnnuitiesAndOtherStateBenefits;
    }

    public TaxRefunds getRefunds() {
        return refunds;
    }

    public List<EmploymentTax> getEmployments() {
        return employments;
    }

}
