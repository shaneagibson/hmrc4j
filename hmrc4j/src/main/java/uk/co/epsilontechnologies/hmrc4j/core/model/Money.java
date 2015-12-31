package uk.co.epsilontechnologies.hmrc4j.core.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Money extends BigDecimal {

    private Money(char[] in, int offset, int len) {
        super(in, offset, len);
    }

    private Money(char[] in, int offset, int len, MathContext mc) {
        super(in, offset, len, mc);
    }

    private Money(char[] in) {
        super(in);
    }

    private Money(char[] in, MathContext mc) {
        super(in, mc);
    }

    public Money(String val) {
        super(val);
    }

    private Money(String val, MathContext mc) {
        super(val, mc);
    }

    private Money(double val) {
        super(val);
    }

    private Money(double val, MathContext mc) {
        super(val, mc);
    }

    private Money(BigInteger val) {
        super(val);
    }

    private Money(BigInteger val, MathContext mc) {
        super(val, mc);
    }

    private Money(BigInteger unscaledVal, int scale) {
        super(unscaledVal, scale);
    }

    private Money(BigInteger unscaledVal, int scale, MathContext mc) {
        super(unscaledVal, scale, mc);
    }

    private Money(int val) {
        super(val);
    }

    private Money(int val, MathContext mc) {
        super(val, mc);
    }

    private Money(long val) {
        super(val);
    }

    private Money(long val, MathContext mc) {
        super(val, mc);
    }

}
