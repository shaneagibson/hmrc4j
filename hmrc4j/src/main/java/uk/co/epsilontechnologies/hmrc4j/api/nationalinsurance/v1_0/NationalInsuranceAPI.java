package uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0.model.AnnualNicsSummary;
import uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0.model.Class1;
import uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0.model.Class2;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcContext;
import uk.co.epsilontechnologies.hmrc4j.core.model.Money;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop.UserRestricted;

import static java.lang.String.format;

public class NationalInsuranceAPI extends API {



    public NationalInsuranceAPI(final HmrcContext hmrcContext) {
        super(hmrcContext);
    }

    @Override
    protected String version() {
        return "1.0";
    }

    @Override
    protected String context() {
        return "national-insurance";
    }

    @UserRestricted(scope = Scope.READ_NATIONAL_INSURANCE)
    public AnnualNicsSummary fetchAnnualNationalInsuranceContributionsSummary(final UTR saUtr, final TaxYear taxYear) throws InvalidUTRException, InvalidTaxYearException {
        try {
            final GetRequest request = get(format("/sa/%s/annual-summary/%s", saUtr.getValue(), taxYear.getValue()));
            addHeader(request, "Accept", formatAcceptHeader("json"));
            addHeader(request, "Authorization", String.format("Bearer %s", userToken().orElse("")));
            return handleResponse(request.asJson());
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private AnnualNicsSummary handleResponse(final HttpResponse<JsonNode> jsonResponse) throws InvalidUTRException, InvalidTaxYearException {
        switch (jsonResponse.getStatus()) {
            case 200 : return toAnnualNicsSummary(jsonResponse.getBody().getObject());
            case 400 : {
                if (isInvalidUtr(jsonResponse)) throw handleInvalidUtr(jsonResponse);
                if (isInvalidTaxYear(jsonResponse)) throw handleInvalidTaxYear(jsonResponse);
            }
            default : throw handleUnexpectedResponse(jsonResponse);
        }
    }

    private AnnualNicsSummary toAnnualNicsSummary(final JSONObject json) {
        return new AnnualNicsSummary(
                new Class1(new Money(String.valueOf(json.getJSONObject("class1").get("totalNICableEarnings")))),
                new Class2(new Money(String.valueOf(json.getJSONObject("class2").get("totalDue")))),
                json.getBoolean("maxNICsReached"));
    }

}