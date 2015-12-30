package uk.co.epsilontechnologies.hmrc4j.core.model;

import static uk.co.epsilontechnologies.hmrc4j.core.model.Require.require;

public class UTR {

    private final String value;

    public UTR(final String value) {
        require(isValid(value), "UTR is not valid");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isValid(final String value) {
        return true; // TODO
    }

}
