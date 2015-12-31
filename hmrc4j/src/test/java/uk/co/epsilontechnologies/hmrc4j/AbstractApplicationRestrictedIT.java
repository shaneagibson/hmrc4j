package uk.co.epsilontechnologies.hmrc4j;

import uk.co.epsilontechnologies.hmrc4j.core.Hmrc;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractApplicationRestrictedIT extends AbstractIT {

    @Override
    protected Hmrc createHmrc() {
        return HmrcFactory.createForApplicationRestrictedAccess(getHmrcCredentials());
    }

}