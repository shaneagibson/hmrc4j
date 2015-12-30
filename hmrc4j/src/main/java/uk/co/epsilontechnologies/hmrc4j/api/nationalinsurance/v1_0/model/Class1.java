package uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0.model;

import uk.co.epsilontechnologies.hmrc4j.core.model.Money;

public class Class1 {

    private final Money totalNICableEarnings;

    public Class1(final Money totalNICableEarnings) {
        this.totalNICableEarnings = totalNICableEarnings;
    }

    public Money getTotalNICableEarnings() {
        return totalNICableEarnings;
    }

}
