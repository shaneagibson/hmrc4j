package uk.co.epsilontechnologies.hmrc4j.api.nationalinsurance.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
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
            final HttpResponse<JsonNode> jsonResponse = Unirest
                    .get(url(String.format("/sa/%s/annual-summary/%s", saUtr.getValue(), taxYear.getValue())))
                    .header("Authorization", String.format("Bearer %s", userToken()))
                    .header("Accept", acceptHeader("json")).asJson();
            switch (jsonResponse.getStatus()) {
                case 200 : return toAnnualNicsSummary(jsonResponse.getBody().getObject());
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

    private AnnualNicsSummary toAnnualNicsSummary(final JSONObject json) {
        return new AnnualNicsSummary(
                new Class1(new Money(json.getString("totalNICableEarnings"))),
                new Class2(new Money(json.getString("totalDue"))),
                json.getBoolean("maxNICsReached"));
    }

}