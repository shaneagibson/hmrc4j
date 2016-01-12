package uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * Aspect for checking that an OAuth 2.0 Token is valid for the User-restricted endpoint that is being invoked.
 * This checks both the scope of the token against the required scope of the endpoint, and also ensures that the token
 * has not expired. If it has, the token is refreshed prior to invoking the endpoint.
 */
@Aspect
public class OAuth20TokenAspect {

    /**
     * The logger for OAuth20TokenAspect
     */
    private static final Log LOG = LogFactory.getLog(OAuth20TokenAspect.class);

    /**
     * Pointcut for the execution of any public, non-static API method invocation.
     */
    @Pointcut("execution(public !static * uk.co.epsilontechnologies.hmrc4j.core.API+.*(..))")
    public void publicApiMethodInvocation() { }

    /**
     * Before advice for the execution of any public, non-static API method invocation, that is also declared as being
     * a User-restricted endpoint.
     *
     * @param joinPoint the join-point for the invocation
     * @param userRestricted the user restricted endpoint details
     */
    @Before("publicApiMethodInvocation() && @annotation(userRestricted)")
    public void checkOAuthToken(final JoinPoint joinPoint, final UserRestricted userRestricted) throws Throwable {

        LOG.debug("checking OAuth 2.0 Token");

        final TokenManager tokenManager = getTokenManager((API) joinPoint.getThis());

        final Token token = getToken(tokenManager);

        if (!hasRequiredScope(token, userRestricted.scope())) {
            throw new TokenInvalidForScopeException(token, userRestricted.scope());
        }

        if (token.isExpired()) {
            LOG.debug("refreshing OAuth 2.0 Token");
            tokenManager.refreshToken();
        }

    }

    /**
     * Gets the token manager for the given API.
     * @param api the API being invoked
     * @return the token manager
     */
    private TokenManager getTokenManager(final API api) {
        return api
                .getContext()
                .getTokenManager()
                .orElseThrow(() -> new IllegalStateException("no token manager was configured"));
    }

    /**
     * Gets the OAuth 2.0 token from the given token manager
     * @param tokenManager the token manager
     * @return the OAuth 2.0 token
     */
    private Token getToken(final TokenManager tokenManager) {
        return tokenManager
                .getToken()
                .orElseThrow(TokenNotFoundException::new);
    }

    /**
     * Checks that the OAuth 2.0 token has the required scope.
     * @param token the token to check
     * @param requiredScope the required scope
     * @return true if the token has the required scope, false otherwise.
     */
    private boolean hasRequiredScope(final Token token, final Scope requiredScope) {
        return token.getScope().contains(requiredScope);
    }

}