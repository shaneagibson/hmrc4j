package uk.co.epsilontechnologies.hmrc4j.core.oauth20.servlet;

import uk.co.epsilontechnologies.hmrc4j.core.Hmrc;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcFactory;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidStateException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.RedirectUriMismatchException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.UserDeniedAuthorizationException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * An abstract Redirect servlet for an OAuth 2.0 Redirect.
 * This will receive the authorization_code from HMRC's OAuth 2.0 Service (along with the optional scope).
 * It will subsequently exchange the authorization_code for an OAuth 2.0 token (using the Token endpoint) and
 * then persist that token according to the implementation of the Token Store - resolved via the
 * getTokenStore(HttpStatusRequest) method implementation.
 */
public abstract class AbstractAuthorizeRedirectServlet extends HttpServlet {

    /**
     * The HMRC Credentials for the client
     */
    private final HmrcCredentials hmrcCredentials;

    /**
     * Default Constructor
     * @param hmrcCredentials the HMRC credentials for the client
     */
    public AbstractAuthorizeRedirectServlet(final HmrcCredentials hmrcCredentials) {
        this.hmrcCredentials = hmrcCredentials;
    }

    /**
     * Handles the authorization and sends the appropriate redirect.
     * @param request the HTTP request
     * @param response the HTTP response
     * @exception IOException   if an input or output error is detected when the servlet handles the GET request
     * @exception ServletException  if the request for the GET could not be handled
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        try {
            handleAuthorization(request);
            response.sendRedirect(successRedirect());
        } catch (final Exception e) {
            response.sendRedirect(failureRedirect(e));
        }
    }

    /**
     * Returns the redirect location for a successful redirect
     * @return the redirect location
     */
    protected abstract String successRedirect();

    /**
     * Returns the redirect location for an unsuccessful redirect
     * @param exception the exception that caused the failure
     * @return the redirect location
     */
    protected abstract String failureRedirect(final Exception exception);

    /**
     * Resolves the token store for the given HTTP Request. The implementation of this is up to each individual client.
     * This may be based on a path parameter (i.e. the URL may contain unique user identifier in the URL), or the client
     * may only need to ever maintain a single token, in which case the URL may be generic.
     * @param request the HTTP request
     * @return the Token Store for the HTTP Request
     */
    protected abstract TokenStore getTokenStore(final HttpServletRequest request);

    /**
     * Performs the state validation, then exchanges the authorization_code for a token.
     * @param request the HTTP request
     * @throws UserDeniedAuthorizationException the user denied the authorization during the OAuth 2.0 Grant screen.
     * @throws RedirectUriMismatchException the Redirect URI does not match
     */
    private void handleAuthorization(final HttpServletRequest request) throws UserDeniedAuthorizationException, RedirectUriMismatchException {

        final TokenStore tokenStore = getTokenStore(request);

        if (!isStateValid(request, tokenStore)) {
            throw new InvalidStateException();
        }

        final String authorizationCode = optionalParameter("code", request).orElseThrow(UserDeniedAuthorizationException::new);

        final Hmrc hmrc = HmrcFactory.createForUserRestrictedAccess(hmrcCredentials, tokenStore);

        hmrc.exchange(authorizationCode);
    }

    /**
     * Determins if the state in the request is the same as the state in the given token store.
     * @param request the HTTP request to check
     * @param tokenStore the Token Store maintaining the original state
     * @return true if the state is valid, false otherwise
     */
    private boolean isStateValid(final HttpServletRequest request, final TokenStore tokenStore) {
        final Optional<String> state = optionalParameter("state", request);
        if (tokenStore.getState().isPresent()) {
            if (!tokenStore.getState().get().equals(state.orElse(""))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resolves the parameter as an java.lang.Optional from the HTTP request.
     * @param key the key for the parameter
     * @param request the http request
     * @return the optional parameter value
     */
    private Optional<String> optionalParameter(final String key, final HttpServletRequest request) {
        return Optional.ofNullable(parameter(key, request));
    }

    /**
     * Resolves the parameter value from the HTTP request.
     * @param key the key for the parameter
     * @param request the http request
     * @return the parameter value
     */
    private String parameter(final String key, final HttpServletRequest request) {
        return request.getParameter(key);
    }

}