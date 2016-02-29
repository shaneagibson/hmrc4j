package uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0;

import org.junit.Assert;
import org.junit.Test;
import uk.co.epsilontechnologies.hmrc4j.AbstractUserRestrictedIT;
import uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0.model.MarriageAllowanceStatus;
import uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0.model.error.UnmatchedRecipientException;
import uk.co.epsilontechnologies.hmrc4j.core.model.NINO;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidNINOException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckMarriageAllowanceEligibilityIT extends AbstractUserRestrictedIT {

    @Test
    public void shouldCheckMarriageAllowanceEligibility() throws InvalidNINOException, InvalidTaxYearException, UnmatchedRecipientException {
        final NINO nino = new NINO("AA000003D");
        final TaxYear taxYear = new TaxYear("2014-15");
        final String firstname = "John";
        final String surname = "Smith";
        final Date dateOfBirth = parseDate("1981-01-31");
        final boolean result = getAPI(MarriageAllowanceAPI.class).checkMarriageAllowanceEligibility(nino, firstname, surname, dateOfBirth, taxYear);
        Assert.assertTrue(result);
    }

    private static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }

    protected Scope getScope() {
        return Scope.READ_MARRIAGE_ALLOWANCE;
    }

}