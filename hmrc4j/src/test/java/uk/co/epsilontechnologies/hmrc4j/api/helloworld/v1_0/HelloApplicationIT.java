package uk.co.epsilontechnologies.hmrc4j.api.helloworld.v1_0;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractApplicationRestrictedIT;

@Ignore
public class HelloApplicationIT extends AbstractApplicationRestrictedIT {

    @Test
    public void shouldSayHelloApplication() {
        final String message = getAPI(HelloWorldAPI.class).sayHelloApplication();
        Assert.assertEquals("Hello Sandbox Application", message);
    }

}