package uk.co.epsilontechnologies.hmrc4j.api.individualbenefits.v1_0.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import uk.co.epsilontechnologies.hmrc4j.core.model.Money;
import uk.co.epsilontechnologies.hmrc4j.core.model.PAYEReference;

public class EmploymentBenefits {

    private final PAYEReference employerPayeReference;
    private final Money companyCarsAndVansBenefit;
    private final Money fuelForCompanyCarsAndVansBenefit;
    private final Money privateMedicalDentalInsurance;
    private final Money vouchersCreditCardsExcessMileageAllowance;
    private final Money goodsEtcProvidedByEmployer;
    private final Money accommodationProvidedByEmployer;
    private final Money otherBenefits;
    private final Money expensesPaymentsReceived;

    public EmploymentBenefits(
            final PAYEReference employerPayeReference,
            final Money companyCarsAndVansBenefit,
            final Money fuelForCompanyCarsAndVansBenefit,
            final Money privateMedicalDentalInsurance,
            final Money vouchersCreditCardsExcessMileageAllowance,
            final Money goodsEtcProvidedByEmployer,
            final Money accommodationProvidedByEmployer,
            final Money otherBenefits,
            final Money expensesPaymentsReceived) {
        this.employerPayeReference = employerPayeReference;
        this.companyCarsAndVansBenefit = companyCarsAndVansBenefit;
        this.fuelForCompanyCarsAndVansBenefit = fuelForCompanyCarsAndVansBenefit;
        this.privateMedicalDentalInsurance = privateMedicalDentalInsurance;
        this.vouchersCreditCardsExcessMileageAllowance = vouchersCreditCardsExcessMileageAllowance;
        this.goodsEtcProvidedByEmployer = goodsEtcProvidedByEmployer;
        this.accommodationProvidedByEmployer = accommodationProvidedByEmployer;
        this.otherBenefits = otherBenefits;
        this.expensesPaymentsReceived = expensesPaymentsReceived;
    }

    public PAYEReference getEmployerPayeReference() {
        return employerPayeReference;
    }

    public Money getCompanyCarsAndVansBenefit() {
        return companyCarsAndVansBenefit;
    }

    public Money getFuelForCompanyCarsAndVansBenefit() {
        return fuelForCompanyCarsAndVansBenefit;
    }

    public Money getPrivateMedicalDentalInsurance() {
        return privateMedicalDentalInsurance;
    }

    public Money getVouchersCreditCardsExcessMileageAllowance() {
        return vouchersCreditCardsExcessMileageAllowance;
    }

    public Money getGoodsEtcProvidedByEmployer() {
        return goodsEtcProvidedByEmployer;
    }

    public Money getAccommodationProvidedByEmployer() {
        return accommodationProvidedByEmployer;
    }

    public Money getOtherBenefits() {
        return otherBenefits;
    }

    public Money getExpensesPaymentsReceived() {
        return expensesPaymentsReceived;
    }

    @Override
    public boolean equals(final Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}