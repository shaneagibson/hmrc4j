package uk.co.epsilontechnologies.hmrc4j.api.individualbenefits.v1_0;

import org.junit.Assert;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUserRestrictedIT;
import uk.co.epsilontechnologies.hmrc4j.api.helloworld.v1_0.HelloWorldAPI;
import uk.co.epsilontechnologies.hmrc4j.api.individualbenefits.v1_0.model.AnnualBenefitsSummary;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

public class FetchAnnualBenefitsSummaryIT extends AbstractUserRestrictedIT {

    @Test
    public void shouldFetchAnnualBenefitsSummary() throws InvalidUTRException, InvalidTaxYearException {
        final UTR saUtr = new UTR("2234567890");
        final TaxYear taxYear = new TaxYear("2014-15");
        final AnnualBenefitsSummary result = getAPI(IndividualBenefitsAPI.class).fetchAnnualBenefitsSummary(saUtr, taxYear);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getEmployments());
        Assert.assertTrue(result.getEmployments().size() > 0);
    }

    protected Scope getScope() {
        return Scope.READ_INDIVIDUAL_BENEFITS;
    }

}