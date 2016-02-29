package uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0.model;

public class MarriageAllowanceStatus {

    private final Status status;
    private final boolean deceased;

    public MarriageAllowanceStatus(
            final Status status,
            final boolean deceased) {
        this.status = status;
        this.deceased = deceased;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isDeceased() {
        return deceased;
    }

    public enum Status {

        RECIPIENT("Recipient"),
        TRANSFEROR("Transferor"),
        NONE("None");

        final String value;

        Status(final String value) {
            this.value = value;
        }

        public static Status forValue(final String value) {
            for (final Status status : values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException(String.format("Invalid status value: %s", value));
        }

    }

}