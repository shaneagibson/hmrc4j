package uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model;

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

}
