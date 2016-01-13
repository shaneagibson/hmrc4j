package uk.co.epsilontechnologies.hmrc4j.api.helloworld.v1_0;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.core.Hmrc;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcImpl;
import uk.co.epsilontechnologies.primer.Primable;
import uk.co.epsilontechnologies.primer.Primer;

import static uk.co.epsilontechnologies.primer.PrimerStatics.*;
import static uk.co.epsilontechnologies.primer.domain.RequestBuilder.*;
import static uk.co.epsilontechnologies.primer.domain.SimpleResponseBuilder.response;

public class HelloWorldRedirectTest {

    @Primable(contextPath = "/hello", port = 9000)
    private Primer hmrcServer;

    @Before
    public void setUp() {
        initPrimers(this);
        this.hmrcServer.start();
    }

    @After
    public void tearDown() {
        this.hmrcServer.stop();
    }

    @Test
    public void shouldRespectPermanentRedirect() {
        shouldRespectRedirect(301);
    }

    @Test
    public void shouldRespectTemporaryRedirect() {
        shouldRespectRedirect(307);
    }

    private void shouldRespectRedirect(final int redirectHttpStatusCode) {

        when(this.hmrcServer.receives(get().withUri("/world"))).thenReturn(response(redirectHttpStatusCode).withHeader("Location", "http://localhost:9000/hello/new"));
        when(this.hmrcServer.receives(get().withUri("/new"))).thenReturn(response(200).withBody("{ \"message\" : \"Hello via Redirect\" }"));

        final Hmrc hmrc = new HmrcImpl("http://localhost:9000");

        final String message = hmrc.getAPI(HelloWorldAPI.class).sayHelloWorld();
        Assert.assertEquals("Hello via Redirect", message);
    }

}
