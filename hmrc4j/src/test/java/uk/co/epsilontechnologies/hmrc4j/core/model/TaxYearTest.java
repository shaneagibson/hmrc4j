package uk.co.epsilontechnologies.hmrc4j.core.model;

import org.junit.Assert;
import org.junit.Test;

public class TaxYearTest {

    @Test
    public void shouldAcceptValidTaxYear() {
        Assert.assertTrue(TaxYear.isValid("2014-15"));
    }

    @Test
    public void shouldRejectValueWithMissingYears() {
        Assert.assertFalse(TaxYear.isValid("2013-15"));
    }

    @Test
    public void shouldAcceptTaxYearAcrossCentury() {
        Assert.assertTrue(TaxYear.isValid("1999-00"));
    }

    @Test
    public void shouldRejectTaxYearWithInvalidCharacters() {
        Assert.assertFalse(TaxYear.isValid("abc"));
        Assert.assertFalse(TaxYear.isValid("2013-1"));
        Assert.assertFalse(TaxYear.isValid("2013-155"));
    }

}