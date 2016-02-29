package uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0.model.MarriageAllowanceStatus;
import uk.co.epsilontechnologies.hmrc4j.api.marriageallowance.v1_0.model.error.UnmatchedRecipientException;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcContext;
import uk.co.epsilontechnologies.hmrc4j.core.model.*;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidNINOException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop.UserRestricted;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.format;

public class MarriageAllowanceAPI extends API {

    public MarriageAllowanceAPI(final HmrcContext hmrcContext) {
        super(hmrcContext);
    }

    @Override
    protected String version() {
        return "1.0";
    }

    @Override
    protected String context() {
        return "marriage-allowance";
    }

    @UserRestricted(scope = Scope.READ_MARRIAGE_ALLOWANCE)
    public MarriageAllowanceStatus fetchMarriageAllowanceStatus(final UTR saUtr, final TaxYear taxYear) throws InvalidUTRException, InvalidTaxYearException {
        try {
            final GetRequest request = get(format("/sa/%s/status?taxYear=%s", saUtr.getValue(), taxYear.getValue()));
            addHeader(request, "Accept", formatAcceptHeader("json"));
            addHeader(request, "Authorization", String.format("Bearer %s", userToken().orElse("")));
            return handleFetchMarriageAllowanceStatusResponse(request.asJson());
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    @UserRestricted(scope = Scope.READ_MARRIAGE_ALLOWANCE)
    public boolean checkMarriageAllowanceEligibility(
            final NINO nino,
            final String firstname,
            final String surname,
            final Date dateOfBirth,
            final TaxYear taxYear) throws InvalidNINOException, InvalidTaxYearException, UnmatchedRecipientException {
        try {
            final GetRequest request = get(format("/eligibility?nino=%s&firstname=%s&surname=%s&dateOfBirth=%s&taxYear=%s", nino.getValue(), firstname, surname, formatDate(dateOfBirth), taxYear.getValue()));
            addHeader(request, "Accept", formatAcceptHeader("json"));
            addHeader(request, "Authorization", String.format("Bearer %s", userToken().orElse("")));
            return handleCheckMarriageAllowanceEligibilityResponse(request.asJson());
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean handleCheckMarriageAllowanceEligibilityResponse(final HttpResponse<JsonNode> jsonResponse) throws InvalidNINOException, InvalidTaxYearException, UnmatchedRecipientException {
        switch (jsonResponse.getStatus()) {
            case 200 : return jsonResponse.getBody().getObject().getBoolean("eligible");
            case 400 : {
                if (isInvalidNino(jsonResponse)) throw handleInvalidNino(jsonResponse);
                if (isInvalidTaxYear(jsonResponse)) throw handleInvalidTaxYear(jsonResponse);
                // invalid date of birth should be prevented by strongly-typed date in request, so let this fall through to handleUnexpectedResponse
            }
            case 404 : throw new UnmatchedRecipientException();
            default : throw handleUnexpectedResponse(jsonResponse);
        }
    }

    private MarriageAllowanceStatus handleFetchMarriageAllowanceStatusResponse(final HttpResponse<JsonNode> jsonResponse) throws InvalidUTRException, InvalidTaxYearException {
        switch (jsonResponse.getStatus()) {
            case 200 : return toMarriageAllowanceStatus(jsonResponse.getBody().getObject());
            case 400 : {
                if (isInvalidUtr(jsonResponse)) throw handleInvalidUtr(jsonResponse);
                if (isInvalidTaxYear(jsonResponse)) throw handleInvalidTaxYear(jsonResponse);
            }
            default : throw handleUnexpectedResponse(jsonResponse);
        }
    }

    private MarriageAllowanceStatus toMarriageAllowanceStatus(final JSONObject json) {
        return new MarriageAllowanceStatus(
                MarriageAllowanceStatus.Status.forValue(json.getString("status")),
                json.getBoolean("deceased"));
    }

}