package uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model;

import uk.co.epsilontechnologies.hmrc4j.core.model.Money;

public class TaxRefunds {

    private final Money taxRefundedOrSetOff;

    public TaxRefunds(final Money taxRefundedOrSetOff) {
        this.taxRefundedOrSetOff = taxRefundedOrSetOff;
    }

    public Money getTaxRefundedOrSetOff() {
        return taxRefundedOrSetOff;
    }

}
