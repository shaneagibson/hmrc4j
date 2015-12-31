package uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0;

import org.junit.Assert;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUserRestrictedIT;
import uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0.model.AnnualNicsSummary;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

public class FetchAnnualNationalInsuranceContributionSummaryIT extends AbstractUserRestrictedIT {

    @Test
    public void shouldFetchAnnualNationalInsuranceContributionsSummary() throws InvalidUTRException, InvalidTaxYearException {
        final UTR saUtr = new UTR("2234567890");
        final TaxYear taxYear = new TaxYear("2014-15");
        final AnnualNicsSummary result = getAPI(NationalInsuranceAPI.class).fetchAnnualNationalInsuranceContributionsSummary(saUtr, taxYear);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getClass1());
        Assert.assertNotNull(result.getClass2());
    }

    protected Scope getScope() {
        return Scope.READ_NATIONAL_INSURANCE;
    }

}