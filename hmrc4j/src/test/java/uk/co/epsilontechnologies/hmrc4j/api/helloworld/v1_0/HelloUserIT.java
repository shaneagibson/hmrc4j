package uk.co.epsilontechnologies.hmrc4j.api.helloworld.v1_0;

import org.junit.Assert;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUserRestrictedIT;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

public class HelloUserIT extends AbstractUserRestrictedIT {

    @Test
    public void shouldSayHelloUser() {
        final String message = getAPI(HelloWorldAPI.class).sayHelloUser();
        Assert.assertEquals("Hello Sandbox User", message);
    }

    @Override
    protected Scope getScope() {
        return Scope.SAY_HELLO;
    }

}