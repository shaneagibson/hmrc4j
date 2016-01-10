package uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0.model.Employer;
import uk.co.epsilontechnologies.hmrc4j.api.individualemployment.v1_0.model.EmploymentHistories;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcContext;
import uk.co.epsilontechnologies.hmrc4j.core.model.PAYEReference;
import uk.co.epsilontechnologies.hmrc4j.core.model.TaxYear;
import uk.co.epsilontechnologies.hmrc4j.core.model.UTR;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop.UserRestricted;

import java.util.ArrayList;
import java.util.List;

public class IndividualEmploymentAPI extends API {

    public IndividualEmploymentAPI(final HmrcContext hmrcContext) {
        super(hmrcContext);
    }

    @Override
    protected String version() {
        return "1.0";
    }

    @Override
    protected String context() {
        return "individual-employment";
    }

    @UserRestricted(scope = Scope.READ_INDIVIDUAL_EMPLOYMENT)
    public EmploymentHistories fetchEmploymentHistory(final UTR saUtr, final TaxYear taxYear) throws InvalidUTRException, InvalidTaxYearException {
        try {
            final GetRequest request = get(String.format("/sa/%s?taxYear=%s", saUtr.getValue(), taxYear.getValue()));
            addHeader(request, "Accept", formatAcceptHeader("json"));
            addHeader(request, "Authorization", String.format("Bearer %s", userToken().orElse("")));
            return handleResponse(request.asJson());
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private EmploymentHistories handleResponse(final HttpResponse<JsonNode> jsonResponse) throws InvalidUTRException, InvalidTaxYearException {
        switch (jsonResponse.getStatus()) {
            case 200 : return toEmploymentHistories(jsonResponse.getBody().getObject());
            case 400 : {
                if (isInvalidUtr(jsonResponse)) throw handleInvalidUtr(jsonResponse);
                if (isInvalidTaxYear(jsonResponse)) throw handleInvalidTaxYear(jsonResponse);
            }
            default : throw handleUnexpectedResponse(jsonResponse);
        }
    }

    private EmploymentHistories toEmploymentHistories(final JSONObject json) {
        final List<Employer> employers = new ArrayList<>();
        final JSONArray employersJson = (JSONArray) json.get("employers");
        for (int i = 0; i < employersJson.length(); i++) {
            final JSONObject employerJson = (JSONObject) employersJson.get(i);
            employers.add(toEmployer(employerJson));
        }
        return new EmploymentHistories(employers);
    }

    private Employer toEmployer(final JSONObject json) {
        return new Employer(
                new PAYEReference(json.getString("payeReference")),
                json.getString("name"));
    }

}