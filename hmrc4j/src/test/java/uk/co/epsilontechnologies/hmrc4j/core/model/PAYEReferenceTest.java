package uk.co.epsilontechnologies.hmrc4j.core.model;

import org.junit.Assert;
import org.junit.Test;

public class PAYEReferenceTest {

    @Test
    public void shouldAcceptValidPAYEReference() {
        Assert.assertTrue(PAYEReference.isValid("123/AB123"));
    }

    @Test
    public void shouldRejectNullValue() {
        Assert.assertFalse(PAYEReference.isValid(null));
    }

    @Test
    public void shouldRejectInvalidValue() {
        Assert.assertFalse(PAYEReference.isValid("ABC/AB123"));
        Assert.assertFalse(PAYEReference.isValid("123AB123"));
        Assert.assertFalse(PAYEReference.isValid("123/$B123"));
    }

}