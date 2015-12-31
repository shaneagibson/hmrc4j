package uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.co.epsilontechnologies.hmrc4j.core.model.Money;

public class Class1 {

    private final Money totalNICableEarnings;

    public Class1(final Money totalNICableEarnings) {
        this.totalNICableEarnings = totalNICableEarnings;
    }

    public Money getTotalNICableEarnings() {
        return totalNICableEarnings;
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
