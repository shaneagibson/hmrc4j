package uk.co.epsilontechnologies.hmrc4j.core.model.error;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException() {
        super();
    }

    @Override
    public String getMessage() {
        return "token not found";
    }

}