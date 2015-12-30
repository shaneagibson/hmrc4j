package uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.TokenInvalidForScopeException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.TokenNotFoundException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenManager;

@Aspect
public class OAuth20TokenAdvice {

    @Pointcut("execution(public !static * uk.co.epsilontechnologies.hmrc4j.core.API+.*(..))")
    public void publicApiMethodInvocation() { }

    @Before("publicApiMethodInvocation() && @annotation(userRestricted)")
    public void checkAuthToken(final JoinPoint joinPoint, final UserRestricted userRestricted) throws Throwable {

        final TokenManager tokenManager = getTokenManager((API) joinPoint.getThis());

        final Token token = getToken(tokenManager);

        if (!hasRequiredScope(token, userRestricted.scope())) {
            throw new TokenInvalidForScopeException(token, userRestricted.scope());
        }

        if (token.isExpired()) {
            tokenManager.refreshToken();
        }

    }

    private TokenManager getTokenManager(final API api) {
        return api
                .getContext()
                .getTokenManager()
                .orElseThrow(() -> new IllegalStateException("no token manager was configured"));
    }

    private Token getToken(final TokenManager tokenManager) {
        return tokenManager
                .getToken()
                .orElseThrow(TokenNotFoundException::new);
    }

    private boolean hasRequiredScope(final Token token, final Scope requiredScope) {
        return token.getScope().contains(requiredScope);
    }

}