package uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model.AnnualIncomeSummary;
import uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model.EmploymentIncome;
import uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0.model.PensionsAnnuitiesAndOtherStateBenefitsIncome;
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

public class IndividualIncomeAPI extends API {

    public IndividualIncomeAPI(final HmrcContext hmrcContext) {
        super(hmrcContext);
    }

    @Override
    protected String version() {
        return "1.0";
    }

    @Override
    protected String context() {
        return "individual-income";
    }

    @UserRestricted(scope = Scope.READ_INDIVIDUAL_INCOME)
    public AnnualIncomeSummary fetchAnnualIncomeSummary(final UTR saUtr, final TaxYear taxYear) throws InvalidUTRException, InvalidTaxYearException {
        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest
                    .get(url(String.format("/sa/%s/annual-summary/%s", saUtr.getValue(), taxYear.getValue())))
                    .header("Authorization", String.format("Bearer %s", userToken()))
                    .header("Accept", acceptHeader("json")).asJson();
            switch (jsonResponse.getStatus()) {
                case 200 : return toAnnualIncomeSummary(jsonResponse.getBody().getObject());
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

    private AnnualIncomeSummary toAnnualIncomeSummary(final JSONObject json) {
        final List<EmploymentIncome> employments = new ArrayList<>();
        final JSONArray employmentsJson = (JSONArray) json.get("employments");
        for (int i = 0; i < employmentsJson.length(); i++) {
            final JSONObject employmentJson = (JSONObject) employmentsJson.get(i);
            employments.add(toEmploymentIncome(employmentJson));
        }
        final JSONObject pensionsAnnuitiesAndOtherStateBenefitsJson = (JSONObject) json.get("pensionsAnnuitiesAndOtherStateBenefits");
        return new AnnualIncomeSummary(toPensionsAnnuitiesAndOtherStateBenefitsIncome(pensionsAnnuitiesAndOtherStateBenefitsJson), employments);
    }

    private PensionsAnnuitiesAndOtherStateBenefitsIncome toPensionsAnnuitiesAndOtherStateBenefitsIncome(final JSONObject json) {
        return new PensionsAnnuitiesAndOtherStateBenefitsIncome(
                new Money(json.getString("otherPensionsAndRetirementAnnuities")),
                new Money(json.getString("incapacityBenefit")),
                new Money(json.getString("jobseekersAllowance")));
    }

    private EmploymentIncome toEmploymentIncome(final JSONObject json) {
        return new EmploymentIncome(
                new PAYEReference(json.getString("employerPayeReference")),
                new Money(json.getString("payFromEmployment")));
    }

}