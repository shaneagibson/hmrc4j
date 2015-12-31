package uk.co.epsilontechnologies.hmrc4j.api.helloworld.v1_0;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcContext;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop.ApplicationRestricted;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop.Unrestricted;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.aop.UserRestricted;

import java.util.Optional;

public class HelloWorldAPI extends API {

    public HelloWorldAPI(final HmrcContext hmrcContext) {
        super(hmrcContext);
    }

    @Override
    protected String version() {
        return "1.0";
    }

    @Override
    protected String context() {
        return "hello";
    }

    @Unrestricted
    public String sayHelloWorld() {
        return sayHello("/world", Optional.<String>empty());
    }

    @ApplicationRestricted
    public String sayHelloApplication() {
        return sayHello("/application", serverToken());
    }

    @UserRestricted(scope = Scope.SAY_HELLO)
    public String sayHelloUser() {
        return sayHello("/user", userToken());
    }

    private String sayHello(final String path, final Optional<String> bearerToken) {
        try {
            final HttpResponse<JsonNode> jsonResponse = Unirest
                    .get(url(path))
                    .header("Accept", acceptHeader("json"))
                    .header("Authorization", String.format("Bearer %s", bearerToken.orElse("")))
                    .asJson();
            switch (jsonResponse.getStatus()) {
                case 200 : return jsonResponse.getBody().getObject().getString("message");
                default : throw handleUnexpectedResponse(jsonResponse);
            }
        } catch (final UnirestException e) {
            throw new RuntimeException(e);
        }
    }

}