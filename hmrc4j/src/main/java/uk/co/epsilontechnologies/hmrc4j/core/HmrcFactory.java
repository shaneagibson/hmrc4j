package uk.co.epsilontechnologies.hmrc4j.core;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;

public class HmrcFactory {

    public static Hmrc createForUnrestrictedAccess() {
        return new HmrcImpl();
    }

    public static Hmrc createForApplicationRestrictedAccess(final HmrcCredentials credentials) {
        return new HmrcImpl(credentials);
    }

    public static Hmrc createForUserRestrictedAccess(final HmrcCredentials credentials, final TokenStore tokenStore) {
        return new HmrcImpl(credentials, tokenStore);
    }

}
