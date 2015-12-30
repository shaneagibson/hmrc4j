package uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcContext;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop.UserRestricted;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model.AnnualTaxSummary;
import uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model.EmploymentTax;
import uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model.PensionsAnnuitiesAndOtherStateBenefitsTax;
import uk.co.epsilontechnologies.hmrc4j.api.individualtax.v1_0.model.TaxRefunds;
import uk.co.epsilontechnologies.hmrc4j.core.model.Money;
import uk.co.epsilontechnologies.hmrc4j.core.model.PAYEReference;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;

import java.util.ArrayList;
import java.util.List;

public class IndividualTaxAPI extends API {

    public IndividualTaxAPI(final HmrcContext hmrcContext) {
        super(hmrcContext);
    }

    @Override
    protected String version() {
        return "1.0";
    }

    @Override
    protected String context() {
        return "individual-tax";
    }

    @UserRestricted(scope = Scope.READ_INDIVIDUAL_TAX)
    public AnnualTaxSummary fetchAnnualTaxSummary(final UTR saUtr, final TaxYear taxYear) throws InvalidUTRException, InvalidTaxYearException {
        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest
                    .get(url(String.format("/sa/%s/annual-summary/%s", saUtr.getValue(), taxYear.getValue())))
                    .header("Authorization", String.format("Bearer %s", userToken()))
                    .header("Accept", acceptHeader("json")).asJson();
            switch (jsonResponse.getStatus()) {
                case 200 : return toAnnualTaxSummary(jsonResponse.getBody().getObject());
                case 400 : {
                    if (isInvalidUtr(jsonResponse)) handleInvalidUtr(jsonResponse);
                    if (isInvalidTaxYear(jsonResponse)) handleInvalidTaxYear(jsonResponse);
                }
                default : throw handleUnexpectedResponse(jsonResponse);
            }
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private AnnualTaxSummary toAnnualTaxSummary(final JSONObject json) {
        final List<EmploymentTax> employments = new ArrayList<>();
        final JSONArray employmentsJson = (JSONArray) json.get("employments");
        for (int i = 0; i < employmentsJson.length(); i++) {
            final JSONObject employmentJson = (JSONObject) employmentsJson.get(i);
            employments.add(toEmploymentTax(employmentJson));
        }
        final JSONObject pensionsAnnuitiesAndOtherStateBenefitsJson = (JSONObject) json.get("pensionsAnnuitiesAndOtherStateBenefits");
        final JSONObject refundsJson = (JSONObject) json.get("refunds");
        return new AnnualTaxSummary(
                toPensionsAnnuitiesAndOtherStateBenefitsTax(pensionsAnnuitiesAndOtherStateBenefitsJson),
                toTaxRefunds(refundsJson),
                employments);
    }

    private TaxRefunds toTaxRefunds(final JSONObject json) {
        return new TaxRefunds(new Money(json.getString("taxRefundedOrSetOff")));
    }

    private PensionsAnnuitiesAndOtherStateBenefitsTax toPensionsAnnuitiesAndOtherStateBenefitsTax(final JSONObject json) {
        return new PensionsAnnuitiesAndOtherStateBenefitsTax(
                new Money(json.getString("otherPensionsAndRetirementAnnuities")),
                new Money(json.getString("incapacityBenefit")));
    }

    private EmploymentTax toEmploymentTax(final JSONObject json) {
        return new EmploymentTax(
                new PAYEReference(json.getString("employerPayeReference")),
                new Money(json.getString("taxTakenOffPay")));
    }

}