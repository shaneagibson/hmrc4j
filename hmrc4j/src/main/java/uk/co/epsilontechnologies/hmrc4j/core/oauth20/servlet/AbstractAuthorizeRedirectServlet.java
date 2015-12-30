package uk.co.epsilontechnologies.hmrc4j.core.oauth20.servlet;

import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidStateException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.RedirectUriMismatchException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.UserDeniedAuthorizationException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.endpoint.TokenEndpoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public abstract class AbstractAuthorizeRedirectServlet extends HttpServlet {

    private final TokenEndpoint tokenEndpoint;

    public AbstractAuthorizeRedirectServlet(final HmrcCredentials hmrcCredentials) {
        this.tokenEndpoint = new TokenEndpoint(hmrcCredentials);
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            handleAuthorization(request);
            response.sendRedirect(request.getContextPath() + onSuccess());
        } catch (final Exception e) {
            response.sendRedirect(request.getContextPath() + onError(e));
        }
    }

    protected abstract String onSuccess();

    protected abstract String onError(final Exception exception);

    protected abstract TokenStore getTokenStore(final HttpServletRequest request);

    private void handleAuthorization(final HttpServletRequest request) throws UserDeniedAuthorizationException, RedirectUriMismatchException {

        final TokenStore tokenStore = getTokenStore(request);

        if (!isStateValid(request, tokenStore)) {
            throw new InvalidStateException();
        }

        final String authorizationCode = optionalParameter("code", request).orElseThrow(UserDeniedAuthorizationException::new);

        final String redirectUri = tokenStore.getRedirectUri().orElseThrow(RedirectUriMismatchException::new);

        tokenStore.setToken(tokenEndpoint.exchange(authorizationCode, redirectUri));
    }

    private boolean isStateValid(final HttpServletRequest request, final TokenStore tokenStore) {
        final Optional<String> state = optionalParameter("state", request);
        if (tokenStore.getState().isPresent()) {
            if (!tokenStore.getState().get().equals(state.orElse(""))) {
                return false;
            }
        }
        return true;
    }

    private Optional<String> optionalParameter(final String key, final HttpServletRequest request) {
        return Optional.ofNullable(parameter(key, request));
    }

    private String parameter(final String key, final HttpServletRequest request) {
        return request.getParameter(key);
    }

}