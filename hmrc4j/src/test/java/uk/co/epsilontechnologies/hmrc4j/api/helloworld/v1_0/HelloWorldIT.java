package uk.co.epsilontechnologies.hmrc4j.api.helloworld.v1_0;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUnrestrictedIT;

@Ignore
public class HelloWorldIT extends AbstractUnrestrictedIT {

    @Test
    public void shouldSayHelloWorld() {
        final String message = getAPI(HelloWorldAPI.class).sayHelloWorld();
        Assert.assertEquals("Hello World", message);
    }

}