package uk.co.epsilontechnologies.hmrc4j.core;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

import java.net.URL;
import java.util.Optional;

/**
 * The access point for all of HMRC's APIs.
 */
public interface Hmrc {

    <T extends API> T getAPI(Class<T> apiClass);

    Optional<URL> getAuthorizeUrl(final Scope... scopes);

    void revokeAuthority();

}