package uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0.model;

import uk.co.epsilontechnologies.hmrc4j.core.model.Money;

public class Class2 {

    private final Money totalDue;

    public Class2(final Money totalDue) {
        this.totalDue = totalDue;
    }

    public Money getTotalDue() {
        return totalDue;
    }

}
