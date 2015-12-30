package uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop;

import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes a User-restricted endpoint.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UserRestricted {

    /**
     * Indicates the scope that is required for this endpoint
     * @return the required scope
     */
    Scope scope();

}
