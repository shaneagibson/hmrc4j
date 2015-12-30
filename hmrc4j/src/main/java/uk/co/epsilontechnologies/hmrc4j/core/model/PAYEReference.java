package uk.co.epsilontechnologies.hmrc4j.core.model;

import static uk.co.epsilontechnologies.hmrc4j.core.model.Require.require;

public class PAYEReference {

    private final String value;

    public PAYEReference(final String value) {
        require(isValid(value), "PAYEReference is not valid");
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isValid(final String value) {
        return true; // TODO
    }

}
