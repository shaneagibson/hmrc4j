package uk.co.epsilontechnologies.hmrc4j.api.individualincome.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
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

import static java.lang.String.format;

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
            final GetRequest request = get(format("/sa/%s/annual-summary/%s", saUtr.getValue(), taxYear.getValue()));
            addHeader(request, "Accept", formatAcceptHeader("json"));
            addHeader(request, "Authorization", String.format("Bearer %s", userToken().orElse("")));
            return handleResponse(request.asJson());
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private AnnualIncomeSummary handleResponse(final HttpResponse<JsonNode> jsonResponse) throws InvalidUTRException, InvalidTaxYearException{
        switch (jsonResponse.getStatus()) {
            case 200 : return toAnnualIncomeSummary(jsonResponse.getBody().getObject());
            case 400 : {
                if (isInvalidUtr(jsonResponse)) throw handleInvalidUtr(jsonResponse);
                if (isInvalidTaxYear(jsonResponse)) throw handleInvalidTaxYear(jsonResponse);
            }
            default : throw handleUnexpectedResponse(jsonResponse);
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
                new Money(String.valueOf(json.get("otherPensionsAndRetirementAnnuities"))),
                new Money(String.valueOf(json.get("incapacityBenefit"))),
                new Money(String.valueOf(json.get("jobseekersAllowance"))));
    }

    private EmploymentIncome toEmploymentIncome(final JSONObject json) {
        return new EmploymentIncome(
                new PAYEReference(json.getString("employerPayeReference")),
                new Money(String.valueOf(json.get("payFromEmployment"))));
    }

}