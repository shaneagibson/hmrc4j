package uk.co.epsilontechnologies.hmrc4j.core.model.error;

public class InvalidStateException extends RuntimeException {

    public InvalidStateException() {
        super();
    }

    public String getMessage() {
        return "invalid state";
    }

}
