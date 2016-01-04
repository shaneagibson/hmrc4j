package uk.co.epsilontechnologies.hmrc4j.sample.auth.servlet;

import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.servlet.AbstractAuthorizeRedirectServlet;

import javax.servlet.http.HttpServletRequest;

public class SampleAuthorizeRedirectServlet extends AbstractAuthorizeRedirectServlet {

    private final TokenStore tokenStore;

    public SampleAuthorizeRedirectServlet(final HmrcCredentials hmrcCredentials, final TokenStore tokenStore) {
        super(hmrcCredentials);
        this.tokenStore = tokenStore;
    }

    @Override
    protected String successRedirect() {
        return "/hmrc4j/sample/connected";
    }

    @Override
    protected String failureRedirect(final Exception exception) {
        return String.format("/hmrc4j/sample/error?error=%s", exception.getMessage());
    }

    @Override
    protected TokenStore getTokenStore(final HttpServletRequest request) {
        return tokenStore;
    }

}