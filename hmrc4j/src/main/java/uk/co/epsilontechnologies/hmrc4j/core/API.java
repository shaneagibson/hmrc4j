package uk.co.epsilontechnologies.hmrc4j.core;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import org.json.JSONObject;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidNINOException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidTaxYearException;
import uk.co.epsilontechnologies.hmrc4j.core.model.error.InvalidUTRException;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Token;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * An abstract superclass for any HMRC API.
 */
public abstract class API {

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
     * Declares the version for this API.
     * @return the version of the API
     */
    protected abstract String version();

    /**
     * Declares the context for this API.
     * @return the context of the API
     */
    protected abstract String context();

    /**
     * Retrieves the context for any API invocation.
     * @return the context of the API
     */
    public HmrcContext getContext() {
        return hmrcContext;
    }

    /**
     * Formats the given media type into an accept header value, containing the version for this API.
     *
     * For example:
     *   "application/vnd.hmrc.1.0+json"
     *
     * @param mediaType the media type (i.e. "xml" or "json")
     * @return the accept header value
     */
    protected String formatAcceptHeader(final String mediaType) {
        return String.format("application/vnd.hmrc.%s+%s", version(), mediaType);
    }

    /**
     * Fetches the optional server_token for this API.
     * @return the server token value
     */
    protected Optional<String> serverToken() {
        return hmrcContext.getCredentials().map(HmrcCredentials::getServerToken);
    }

    /**
     * Fetches the optional OAuth 2.0 access_token (a.k.a the "User" token) for this API.
     * @return the user token value
     */
    protected Optional<String> userToken() {
        return hmrcContext.getTokenManager().map(tokenManager -> tokenManager.getToken().map(Token::getAccessToken)).orElse(Optional.empty());
    }

    /**
     * Prefixes the given path with the base URL and context for this API.
     * @param path the path to prefix
     * @return the full url
     */
    protected String formatUrl(final String path) {
        return String.format("%s/%s%s", hmrcContext.getBaseUrl(), context(), path);
    }

    /**
     * Handles an unexpected API HTTP Response by converting the status and body into a RuntimeException
     * @param jsonResponse the unexpected http response
     * @return a runtime exception
     */
    protected RuntimeException handleUnexpectedResponse(final HttpResponse<JsonNode> jsonResponse) {
        return new RuntimeException(String.format(
                "%s %s %s",
                jsonResponse.getStatus(),
                jsonResponse.getStatusText(),
                jsonResponse.getBody().toString()));
    }

    /**
     * Convenience method for creating a HTTP GET Request for the given path
     * @param path the path to GET
     * @return the HTTP GET Request
     */
    protected GetRequest get(final String path) {
        return Unirest.get(formatUrl(path));
    }

    /**
     * Convenience method for adding a header to the HTTP Request
     * @param request the HTTP request
     * @param key the name of the header to add
     * @param value the value of the header to add
     */
    protected void addHeader(final HttpRequest request, final String key, final String value) {
        request.header(key, value);
    }

    /**
     * Determines if the given API HTTP Response matches the given error code.
     *
     * @param errorCode the expected error code
     * @param jsonResponse the HTTP response to check
     * @return true if the error in the HTTP response is the same as the errorCode parameter, otherwise false.
     */
    protected boolean matchError(final String errorCode, final HttpResponse<JsonNode> jsonResponse) {
        final JSONObject json = jsonResponse.getBody().getObject();
        return json.has("code") && json.getString("code").equals(errorCode);
    }

    /**
     * Determines if the HTTP Response contains an invalid NINO error response.
     *
     * @param jsonResponse the HTTP response to check
     * @return true if the HTTP response contains an Invalid NINO error.
     */
    protected boolean isInvalidNino(final HttpResponse<JsonNode> jsonResponse) {
        return matchError("NINO_INVALID", jsonResponse);
    }

    /**
     * Determines if the HTTP Response contains an invalid UTR error response.
     *
     * @param jsonResponse the HTTP response to check
     * @return true if the HTTP response contains an Invalid SA UTR error.
     */
    protected boolean isInvalidUtr(final HttpResponse<JsonNode> jsonResponse) {
        return matchError("SA_UTR_INVALID", jsonResponse);
    }

    /**
     * Determines if the HTTP Response contains an invalid Tax Year error response.
     *
     * @param jsonResponse the HTTP response to check
     * @return true if the HTTP response contains an Invalid Tax Year error.
     */
    protected boolean isInvalidTaxYear(final HttpResponse<JsonNode> jsonResponse) {
        return matchError("TAX_YEAR_INVALID", jsonResponse);
    }

    /**
     * Handles an Tnvalid Tax Year error by convering the HTTP response into an InvalidTaxYearException.
     * @param jsonResponse the invalid HTTP response
     * @return the invalid tax year exception
     */
    protected InvalidTaxYearException handleInvalidTaxYear(final HttpResponse<JsonNode> jsonResponse) {
        return new InvalidTaxYearException(jsonResponse.getBody().getObject().getString("message"));
    }

    /**
     * Handles an Tnvalid UTR error by convering the HTTP response into an InvalidUTRException.
     * @param jsonResponse the invalid HTTP response
     * @return the invalid UTR exception
     */
    protected InvalidUTRException handleInvalidUtr(final HttpResponse<JsonNode> jsonResponse) {
        return new InvalidUTRException(jsonResponse.getBody().getObject().getString("message"));
    }

    /**
     * Handles an Tnvalid NINO error by convering the HTTP response into an InvalidNINOException.
     * @param jsonResponse the invalid HTTP response
     * @return the invalid NINO exception
     */
    protected InvalidNINOException handleInvalidNino(final HttpResponse<JsonNode> jsonResponse) {
        return new InvalidNINOException(jsonResponse.getBody().getObject().getString("message"));
    }

    /**
     * Formats the given date into a string (using 'yyyy-MM-dd' format).
     * @param date the date to format
     * @return the date as a string, in format 'yyyy-MM-dd
     */
    protected String formatDate(final Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

}