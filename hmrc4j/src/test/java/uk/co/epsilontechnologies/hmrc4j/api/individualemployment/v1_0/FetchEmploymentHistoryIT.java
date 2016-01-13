package uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUserRestrictedIT;
import uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0.model.EmploymentHistories;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

@Ignore
public class FetchEmploymentHistoryIT extends AbstractUserRestrictedIT {

    @Test
    public void shouldEmploymentHistory() throws InvalidUTRException, InvalidTaxYearException {
        final UTR saUtr = new UTR("2234567890");
        final TaxYear taxYear = new TaxYear("2014-15");
        final EmploymentHistories result = getAPI(IndividualEmploymentAPI.class).fetchEmploymentHistory(saUtr, taxYear);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getEmployers());
        Assert.assertTrue(result.getEmployers().size() > 0);
    }

    protected Scope getScope() {
        return Scope.READ_INDIVIDUAL_EMPLOYMENT;
    }

}