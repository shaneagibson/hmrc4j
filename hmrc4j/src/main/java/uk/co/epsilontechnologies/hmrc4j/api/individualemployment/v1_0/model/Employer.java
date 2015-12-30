package uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0.model;

import uk.co.epsilontechnologies.hmrc4j.core.model.PAYEReference;

public class Employer {

    private final PAYEReference payeReference;
    private final String name;

    public Employer(final PAYEReference payeReference, final String name) {
        this.payeReference = payeReference;
        this.name = name;
    }

    public PAYEReference getPayeReference() {
        return payeReference;
    }

    public String getName() {
        return name;
    }

}
