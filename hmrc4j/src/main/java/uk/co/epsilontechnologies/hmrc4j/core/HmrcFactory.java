package uk.co.epsilontechnologies.hmrc4j.core;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;

/**
 * Factory for creating a HMRC object, which can then be used to access HMRC APIs.
 */
public class HmrcFactory {

    /**
     * The base URL for HMRC's API Gateway.
     */
    public static final String BASE_URL = "https://api.service.hmrc.gov.uk";

    /**
     * Creates a HMRC object for unrestricted access. This can only be used to access unrestricted endpoints.
     * @return a HMRC object with unrestricted access
     */
    public static Hmrc createForUnrestrictedAccess() {
        return new HmrcImpl(BASE_URL);
    }

    /**
     * Creates a HMRC object for application-restricted access. This can only be used to access unrestricted or application-restricted endpoints.
     * @param credentials the credentials for the client application
     * @return a HMRC object with application-restricted and unrestricted access
     */
    public static Hmrc createForApplicationRestrictedAccess(final HmrcCredentials credentials) {
        return new HmrcImpl(BASE_URL, credentials);
    }

    /**
     * Creates a HMRC object for user-restricted access. This can be used to access any HMRC endpoints.
     * @param credentials the credentials for the client application
     * @param tokenStore the repository for persisting and accessing OAuth 2.0 user tokens
     * @return a HMRC object with user-restricted, application-restricted and unrestricted access
     */
    public static Hmrc createForUserRestrictedAccess(final HmrcCredentials credentials, final TokenStore tokenStore) {
        return new HmrcImpl(BASE_URL, credentials, tokenStore);
    }

}