package uk.co.epsilontechnologies.hmrc4j.core.model;

import org.junit.Assert;
import org.junit.Test;

public class UTRTest {

    @Test
    public void shouldAcceptValidUTR() {
        Assert.assertTrue(UTR.isValid("1234567890"));
    }

    @Test
    public void shouldRejectNullValue() {
        Assert.assertFalse(UTR.isValid(null));
    }

    @Test
    public void shouldRejectInvalidValue() {
        Assert.assertFalse(UTR.isValid("123456789"));
        Assert.assertFalse(UTR.isValid("12345678900"));
        Assert.assertFalse(UTR.isValid("A234567890"));
    }

}