package uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0.model;

import java.util.List;

public class EmploymentHistories {

    private final List<Employer> employers;

    public EmploymentHistories(final List<Employer> employers) {
        this.employers = employers;
    }

    public List<Employer> getEmployers() {
        return this.employers;
    }

}
