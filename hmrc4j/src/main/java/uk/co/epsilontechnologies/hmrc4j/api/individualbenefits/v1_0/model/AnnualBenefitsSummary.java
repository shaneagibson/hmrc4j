package uk.co.epsilontechnologies.hmrc4j.api.individualbenefits.v1_0.model;

import java.util.List;

public class AnnualBenefitsSummary {

    private final List<EmploymentBenefits> employments;

    public AnnualBenefitsSummary(final List<EmploymentBenefits> employments) {
        this.employments = employments;
    }

    public List<EmploymentBenefits> getEmployments() {
        return employments;
    }

}
