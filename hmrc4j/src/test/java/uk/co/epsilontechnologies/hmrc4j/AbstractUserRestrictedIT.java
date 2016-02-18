package uk.co.epsilontechnologies.hmrc4j;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import uk.co.epsilontechnologies.hmrc4j.core.Hmrc;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcFactory;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.servlet.AbstractAuthorizeRedirectServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractUserRestrictedIT extends AbstractIT {

    protected abstract Scope getScope();

    private boolean initialized = false;

    protected final Server server;
    protected final TokenStore tokenStore;

    public AbstractUserRestrictedIT() {
        this(new InMemoryTokenStore());
    }

    public AbstractUserRestrictedIT(final TokenStore tokenStore) {
        final int port = Integer.parseInt(getConfig("server.port"));
        this.server = new Server(port);
        this.server.setHandler(new ServerRequestHandler());
        this.tokenStore = tokenStore;
        this.tokenStore.setState(UUID.randomUUID().toString());
        this.tokenStore.setRedirectUri(String.format("http://localhost:%s/redirect", port));
    }

    @Override
    protected Hmrc createHmrc() {
        return HmrcFactory.createForUserRestrictedAccess(getHmrcCredentials(), tokenStore);
    }

    @Before
    public void setUp() throws Exception {
        this.startServer();
        this.initializeTokenStore();
    }

    @After
    public void tearDown() {
        this.stopServer();
    }

    protected void startServer() {
        try {
            this.server.start();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void stopServer() {
        try {
            this.server.stop();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void initializeTokenStore() throws Exception {
        final Optional<URL> authorizeUrl = createHmrc().getAuthorizeUrl(getScope());
        final HtmlUnitDriver browser = new HtmlUnitDriver();
        browser.setJavascriptEnabled(true);
        browser.get(authorizeUrl.map(URL::toString).orElseThrow(() -> new IllegalStateException("no authorize url found")));
        browser.findElement(By.id("userId")).sendKeys("user1");
        browser.findElement(By.id("password")).sendKeys("password1");
        browser.findElement(By.tagName("button")).click();
        browser.findElement(By.id("authorise")).click();
        while (!initialized) { /* wait til initialized */ }
    }

    class ServerRequestHandler extends AbstractHandler {

        private final AbstractAuthorizeRedirectServlet redirectServlet = new AbstractAuthorizeRedirectServlet(getHmrcCredentials()) {

            @Override
            protected String successRedirect() {
                return "success";
            }

            @Override
            protected String failureRedirect(final Exception exception) {
                return "failure";
            }

            @Override
            protected TokenStore getTokenStore(final HttpServletRequest request) {
                return tokenStore;
            }

        };

        @Override
        public void handle(
                final String target,
                final org.eclipse.jetty.server.Request baseRequest,
                final HttpServletRequest httpServletRequest,
                final HttpServletResponse httpServletResponse) throws IOException, ServletException {
            switch (target) {
                case "/redirect" : redirectServlet.service(httpServletRequest, httpServletResponse);
                case "/success" : httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                case "/failure" : httpServletResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            initialized = true;
        }

    }

    public static class InMemoryTokenStore implements TokenStore {

        private Optional<Token> token = Optional.empty();
        private Optional<String> redirectUri = Optional.empty();
        private Optional<String> state = Optional.empty();

        @Override
        public void removeToken() {
            this.token = Optional.empty();
        }

        @Override
        public Optional<Token> getToken() {
            return this.token;
        }

        @Override
        public Optional<String> getRedirectUri() {
            return this.redirectUri;
        }

        @Override
        public void setToken(final Token token) {
            this.token = Optional.of(token);
        }

        @Override
        public Optional<String> getState() {
            return this.state;
        }

        @Override
        public void setState(final String state) {
            this.state = Optional.of(state);
        }

        @Override
        public void setRedirectUri(final String redirectUri) {
            this.redirectUri = Optional.of(redirectUri);
        }

    }

}