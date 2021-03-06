package uk.co.epsilontechnologies.hmrc4j;

import uk.co.epsilontechnologies.hmrc4j.core.Hmrc;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcFactory;

public abstract class AbstractApplicationRestrictedIT extends AbstractIT {

    @Override
    protected Hmrc createHmrc() {
        return HmrcFactory.createForApplicationRestrictedAccess(getHmrcCredentials());
    }

}