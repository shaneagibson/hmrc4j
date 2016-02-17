package uk.co.epsilontechnologies.hmrc4j.core.oauth20;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The OAuth 2.0 Scopes that are supported by HMRC's APIs.
 */
public enum Scope {

    // example scope
    SAY_HELLO("hello"),

    // read scopes
    READ_INDIVIDUAL_BENEFITS("read:individual-benefits"),
    READ_INDIVIDUAL_EMPLOYMENT("read:individual-employment"),
    READ_INDIVIDUAL_INCOME("read:individual-income"),
    READ_INDIVIDUAL_TAX("read:individual-tax"),
    READ_NATIONAL_INSURANCE("read:national-insurance");

    /**
     * The key for the scope.
     */
    private String key;

    /**
     * Enum constructor for a Scope.
     *
     * @param key the key for the scope
     */
    Scope(final String key) {
        this.key = key;
    }

    /**
     * Fetches the key for the scope.
     * @return the key for the scope
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the Scope object for the given key
     * @param key the key on which to match
     * @throws IllegalArgumentException if no Scope is found for the given key
     * @return the Scope object
     */
    public static Scope forKey(final String key) {
        for (final Scope scope : Scope.values()) {
            if (scope.getKey().equals(key)) {
                return scope;
            }
        }
        throw new IllegalArgumentException(String.format("Invalid scope %s", key));
    }

    /**
     * Utility method for converting a space-separated String of scopes to a List of Scopes
     *
     * @param scope the space-separated string of scopes to convert
     * @return the list of scopes
     */
    public static List<Scope> asList(final String scope) {
        return Arrays.asList(scope.split(" ")).stream().map(Scope::forKey).collect(Collectors.toList());
    }

    /**
     * Utility method for converting a List of Scopes to a space-separated String.
     *
     * @param scope the list of scopes to convert
     * @return the space-separated string
     */
    public static String asString(final List<Scope> scope) {
        return scope.stream().map(Scope::getKey).collect(Collectors.joining(" "));
    }

}