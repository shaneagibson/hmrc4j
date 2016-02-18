package uk.co.epsilontechnologies.hmrc4j.api.helloworld.v1_0;

import org.junit.Assert;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUserRestrictedIT;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;

public class HelloUserRefreshTokenIT extends AbstractUserRestrictedIT {

    public HelloUserRefreshTokenIT() {
        super(new ExpiringInMemoryTokenStore());
    }

    static class ExpiringInMemoryTokenStore extends AbstractUserRestrictedIT.InMemoryTokenStore {

        private int updateCounter = 0;

        @Override
        public void setToken(final Token token) {
            this.updateCounter++; // we need to count the number of invocations, since HMRC will still return the same token
            super.setToken(
                    new Token(
                            token.getAccessToken(),
                            token.getRefreshToken(),
                            0, // artificially expire the token
                            token.getTokenType(),
                            token.getScope(),
                            token.getCreatedAt()));
        }

        public int getUpdateCounter() {
            return updateCounter;
        }

    }

    @Test
    public void shouldRefreshExpiredTokenWhenSayingHelloUser() {
        Assert.assertEquals(1, ((ExpiringInMemoryTokenStore)super.tokenStore).getUpdateCounter());
        Assert.assertEquals("Hello Sandbox User", getAPI(HelloWorldAPI.class).sayHelloUser());
        Assert.assertEquals(2, ((ExpiringInMemoryTokenStore)super.tokenStore).getUpdateCounter());
        Assert.assertEquals("Hello Sandbox User", getAPI(HelloWorldAPI.class).sayHelloUser());
        Assert.assertEquals(3, ((ExpiringInMemoryTokenStore)super.tokenStore).getUpdateCounter());
    }

    @Override
    protected Scope getScope() {
        return Scope.SAY_HELLO;
    }

}