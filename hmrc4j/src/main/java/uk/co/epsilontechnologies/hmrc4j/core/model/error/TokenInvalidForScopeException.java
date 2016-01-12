package uk.co.epsilontechnologies.hmrc4j.core.model.error;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;

/**
 * The OAuth 2.0 Token that is held in the Token Store does not have the required Scope for the API endpoint that is
 * being requested.
 */
public class TokenInvalidForScopeException extends RuntimeException {

    /**
     * Default Constructor
     * @param token the token
     * @param requiredScope the required scope for the request
     */
    public TokenInvalidForScopeException(final Token token, final Scope requiredScope) {
        super(String.format("token (\"%s\") is not valid for scope %s", Scope.asString(token.getScope()), requiredScope.getKey()));
    }

}