package uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model;

import uk.co.epsilontechnologies.hmrc4j.core.model.Money;
import uk.co.epsilontechnologies.hmrc4j.core.model.PAYEReference;

public class EmploymentIncome {

    private final PAYEReference employerPayeReference;
    private final Money payFromEmployment;

    public EmploymentIncome(final PAYEReference employerPayeReference, final Money payFromEmployment) {
        this.employerPayeReference = employerPayeReference;
        this.payFromEmployment = payFromEmployment;
    }

    public PAYEReference getEmployerPayeReference() {
        return employerPayeReference;
    }

    public Money getPayFromEmployment() {
        return payFromEmployment;
    }

}
