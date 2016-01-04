package uk.co.epsilontechnologies.hmrc4j.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;

import java.util.Optional;

/**
 * An abstract superclass for any HMRC API.
 */
public abstract class API {

    /**
     * The base URL for HMRC's API Gateway.
     */
    public static final String BASE_URL = "https://api.service.hmrc.gov.uk";

    /**
     * The Context for the API invocation.
     * The maintains any credentials or token information that might be required by any API invocations.
     */
    private final HmrcContext hmrcContext;

    /**
     * Instantiates an API.
     * @param hmrcContext the context for any API invocation
     */
    protected API(final HmrcContext hmrcContext) {
        this.hmrcContext = hmrcContext;
    }

    /**
     * Retrieves the context for any API invocation.
     * @return the context of the API
     */
    public HmrcContext getContext() {
        return hmrcContext;
    }

    protected String acceptHeader(final String mediaType) {
        return String.format("application/vnd.hmrc.%s+%s", version(), mediaType);
    }

    protected Optional<String> serverToken() {
        return hmrcContext.getCredentials().map(HmrcCredentials::getServerToken);
    }

    protected Optional<String> userToken() {
        return hmrcContext.getTokenManager().map(tokenManager -> tokenManager.getToken().map(Token::getAccessToken)).orElse(Optional.empty());
    }

    protected String url(final String path) {
        return String.format("%s/%s%s", BASE_URL, context(), path);
    }

    protected RuntimeException handleUnexpectedResponse(final HttpResponse<JsonNode> jsonResponse) {
        return new RuntimeException(String.format(
                "%s %s %s",
                jsonResponse.getStatus(),
                jsonResponse.getStatusText(),
                jsonResponse.getBody().toString()));
    }

    protected abstract String version();

    protected abstract String context();

    protected boolean isError(final String code, final HttpResponse<JsonNode> jsonResponse) {
        final JSONObject json = jsonResponse.getBody().getObject();
        return json.getString("code").equals(code);
    }

    protected boolean isInvalidUtr(final HttpResponse<JsonNode> jsonResponse) {
        return isError("SA_UTR_INVALID", jsonResponse);
    }

    protected boolean isInvalidTaxYear(final HttpResponse<JsonNode> jsonResponse) {
        return isError("TAX_YEAR_INVALID", jsonResponse);
    }

    protected void handleInvalidTaxYear(final HttpResponse<JsonNode> jsonResponse) throws InvalidTaxYearException {
        throw new InvalidTaxYearException(jsonResponse.getBody().getObject().getString("message"));
    }

    protected void handleInvalidUtr(final HttpResponse<JsonNode> jsonResponse) throws InvalidUTRException {
        throw new InvalidUTRException(jsonResponse.getBody().getObject().getString("message"));
    }

}