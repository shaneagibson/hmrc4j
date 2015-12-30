package uk.co.epsilontechnologies.hmrc4j.core.model.error;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;

public class TokenInvalidForScopeException extends RuntimeException {

    private final Token token;
    private final Scope requiredScope;

    public TokenInvalidForScopeException(final Token token, final Scope requiredScope) {
        this.token = token;
        this.requiredScope = requiredScope;
    }

    @Override
    public String getMessage() {
        return String.format("token (\"%s\") is not valid for scope %s", Scope.asString(token.getScope()), requiredScope.getKey());
    }

}