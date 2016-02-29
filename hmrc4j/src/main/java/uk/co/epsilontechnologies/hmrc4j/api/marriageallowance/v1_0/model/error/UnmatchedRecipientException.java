package uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0.model.error;

public class UnmatchedRecipientException extends Exception {

    public UnmatchedRecipientException() {
        super("invalid marriage allowance recipient");
    }

}
