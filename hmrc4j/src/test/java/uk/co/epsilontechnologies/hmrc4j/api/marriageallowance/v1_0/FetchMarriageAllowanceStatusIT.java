package uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0;

import org.junit.Assert;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUserRestrictedIT;
import uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.IndividualTaxAPI;
import uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model.AnnualTaxSummary;
import uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0.model.MarriageAllowanceStatus;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

public class FetchMarriageAllowanceStatusIT extends AbstractUserRestrictedIT {

    @Test
    public void shouldFetchMarriageAllowanceStatus() throws InvalidUTRException, InvalidTaxYearException {
        final UTR saUtr = new UTR("2234567890");
        final TaxYear taxYear = new TaxYear("2014-15");
        final MarriageAllowanceStatus result = getAPI(MarriageAllowanceAPI.class).fetchMarriageAllowanceStatus(saUtr, taxYear);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getStatus());
        Assert.assertEquals(MarriageAllowanceStatus.Status.RECIPIENT, result.getStatus());
        Assert.assertFalse(result.isDeceased());
    }

    protected Scope getScope() {
        return Scope.READ_MARRIAGE_ALLOWANCE;
    }

}