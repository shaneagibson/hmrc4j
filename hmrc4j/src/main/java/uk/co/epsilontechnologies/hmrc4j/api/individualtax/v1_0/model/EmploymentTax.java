package uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model;

import uk.co.epsilontechnologies.hmrc4j.core.model.Money;
import uk.co.epsilontechnologies.hmrc4j.core.model.PAYEReference;

public class EmploymentTax {

    private final PAYEReference employerPayeReference;
    private final Money taxTakenOffPay;

    public EmploymentTax(final PAYEReference employerPayeReference, final Money taxTakenOffPay) {
        this.employerPayeReference = employerPayeReference;
        this.taxTakenOffPay = taxTakenOffPay;
    }

    public PAYEReference getEmployerPayeReference() {
        return employerPayeReference;
    }

    public Money getTaxTakenOffPay() {
        return taxTakenOffPay;
    }

}
