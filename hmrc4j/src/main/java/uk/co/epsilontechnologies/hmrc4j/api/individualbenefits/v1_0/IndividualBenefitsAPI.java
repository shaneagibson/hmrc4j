package uk.co.epsilontechnologies.hmrc4j.api.individualbenefits.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.api.individualbenefits.v1_0.model.AnnualBenefitsSummary;
import uk.co.epsilontechnologies.hmrc4j.api.individualbenefits.v1_0.model.EmploymentBenefits;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcContext;
import uk.co.epsilontechnologies.hmrc4j.core.model.Money;
import uk.co.epsilontechnologies.hmrc4j.core.model.PAYEReference;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop.UserRestricted;

import java.util.ArrayList;
import java.util.List;

public class IndividualBenefitsAPI extends API {

    public IndividualBenefitsAPI(final HmrcContext hmrcContext) {
        super(hmrcContext);
    }

    @Override
    protected String version() {
        return "1.0";
    }

    @Override
    protected String context() {
        return "individual-benefits";
    }

    @UserRestricted(scope = Scope.READ_INDIVIDUAL_BENEFITS)
    public AnnualBenefitsSummary fetchAnnualBenefitsSummary(final UTR saUtr, final TaxYear taxYear) throws InvalidUTRException, InvalidTaxYearException {
        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest
                    .get(url(String.format("/sa/%s/annual-summary/%s", saUtr.getValue(), taxYear.getValue())))
                    .header("Authorization", String.format("Bearer %s", userToken().orElse("")))
                    .header("Accept", acceptHeader("json")).asJson();
            switch (jsonResponse.getStatus()) {
                case 200 : return toAnnualBenefitsSummary(jsonResponse.getBody().getObject());
                case 400 : {
                    if (isInvalidUtr(jsonResponse)) throw handleInvalidUtr(jsonResponse);
                    if (isInvalidTaxYear(jsonResponse)) throw handleInvalidTaxYear(jsonResponse);
                }
                default : throw handleUnexpectedResponse(jsonResponse);
            }
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private AnnualBenefitsSummary toAnnualBenefitsSummary(final JSONObject json) {
        final List<EmploymentBenefits> employments = new ArrayList<>();
        final JSONArray employmentsJson = (JSONArray) json.get("employments");
        for (int i = 0; i < employmentsJson.length(); i++) {
            final JSONObject employmentJson = (JSONObject) employmentsJson.get(i);
            employments.add(toEmploymentBenefits(employmentJson));
        }
        return new AnnualBenefitsSummary(employments);
    }

    private EmploymentBenefits toEmploymentBenefits(final JSONObject json) {
        return new EmploymentBenefits(
                new PAYEReference(json.getString("employerPayeReference")),
                new Money(String.valueOf(json.get("companyCarsAndVansBenefit"))),
                new Money(String.valueOf(json.get("fuelForCompanyCarsAndVansBenefit"))),
                new Money(String.valueOf(json.get("privateMedicalDentalInsurance"))),
                new Money(String.valueOf(json.get("vouchersCreditCardsExcessMileageAllowance"))),
                new Money(String.valueOf(json.get("goodsEtcProvidedByEmployer"))),
                new Money(String.valueOf(json.get("accommodationProvidedByEmployer"))),
                new Money(String.valueOf(json.get("otherBenefits"))),
                new Money(String.valueOf(json.get("expensesPaymentsReceived"))));
    }

}