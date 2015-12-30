package uk.co.epsilontechnologies.hmrc4j.core.model.error;

public class RedirectUriMismatchException extends Exception {

    public String getMessage() {
        return "redirect uri mismatch";
    }

}
