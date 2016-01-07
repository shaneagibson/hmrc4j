package uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUserRestrictedIT;
import uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model.AnnualIncomeSummary;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

@Ignore
public class FetchAnnualIncomeSummaryIT extends AbstractUserRestrictedIT {

    @Test
    public void shouldFetchAnnualIncomeSummary() throws InvalidUTRException, InvalidTaxYearException {
        final UTR saUtr = new UTR("2234567890");
        final TaxYear taxYear = new TaxYear("2014-15");
        final AnnualIncomeSummary result = getAPI(IndividualIncomeAPI.class).fetchAnnualIncomeSummary(saUtr, taxYear);
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getPensionsAnnuitiesAndOtherStateBenefits());
        Assert.assertNotNull(result.getEmployments());
        Assert.assertTrue(result.getEmployments().size() > 0);
    }

    protected Scope getScope() {
        return Scope.READ_INDIVIDUAL_INCOME;
    }

}