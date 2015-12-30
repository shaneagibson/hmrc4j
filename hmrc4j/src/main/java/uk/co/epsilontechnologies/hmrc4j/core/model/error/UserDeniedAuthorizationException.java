package uk.co.epsilontechnologies.hmrc4j.core.model.error;

public class UserDeniedAuthorizationException extends Exception {

    public String getMessage() {
        return "user denied authorization";
    }

}
