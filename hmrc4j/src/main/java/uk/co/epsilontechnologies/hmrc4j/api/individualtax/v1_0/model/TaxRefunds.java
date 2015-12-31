package uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.co.epsilontechnologies.hmrc4j.core.model.Money;

public class TaxRefunds {

    private final Money taxRefundedOrSetOff;

    public TaxRefunds(final Money taxRefundedOrSetOff) {
        this.taxRefundedOrSetOff = taxRefundedOrSetOff;
    }

    public Money getTaxRefundedOrSetOff() {
        return taxRefundedOrSetOff;
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
