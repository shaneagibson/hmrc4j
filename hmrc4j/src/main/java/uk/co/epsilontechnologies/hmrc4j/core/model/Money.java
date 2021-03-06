package uk.co.epsilontechnologies.hmrc4j.core.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

/**
 * A domain-specific wrapper for {@code java.lang.BigDecimal} to represent Money.
 * The currency is always GBP.
 */
public class Money extends BigDecimal {

    public Money(String val) {
        super(val);
    }

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
