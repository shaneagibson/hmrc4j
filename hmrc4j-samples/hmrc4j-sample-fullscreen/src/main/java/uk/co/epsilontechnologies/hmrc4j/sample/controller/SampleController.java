package uk.co.epsilontechnologies.hmrc4j.sample.controller;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.epsilontechnologies.hmrc4j.api.helloworld.v1_0.HelloWorldAPI;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcFactory;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.Scope;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;

import java.net.URL;

@Controller
@RequestMapping(value = "/hmrc4j")
public class SampleController {

    private final HmrcCredentials hmrcCredentials;
    private final TokenStore tokenStore;

    @Autowired
    public SampleController(final HmrcCredentials hmrcCredentials, final TokenStore tokenStore) {
        this.hmrcCredentials = hmrcCredentials;
        this.tokenStore = tokenStore;
    }

    @RequestMapping(value = "/sample")
    public String render(final Model model) {
        model.addAttribute("connected", hasToken());
        model.addAttribute("connectUrl", authorizeUrl(Scope.SAY_HELLO));
        return "index";
    }

    @RequestMapping(value = "/sample/connected")
    public String connected(final Model model) {
        model.addAttribute("message", "Connected to HMRC");
        return render(model);
    }

    @RequestMapping(value = "/sample/error", params = { "error" })
    public String error(@RequestParam("error") final String error, final Model model) {
        model.addAttribute("message", error);
        return render(model);
    }

    @RequestMapping(value = "/sample/revoke")
    public String revoke(final Model model) {
        HmrcFactory.createForUserRestrictedAccess(hmrcCredentials, tokenStore).revokeAuthority();
        return render(model);
    }

    @RequestMapping(value = "/sample/hello/world")
    public String sayHelloWorld(final Model model) {
        final String message = HmrcFactory.createForUnrestrictedAccess().getAPI(HelloWorldAPI.class).sayHelloWorld();
        model.addAttribute("message", message);
        return render(model);
    }

    @RequestMapping(value = "/sample/hello/application")
    public String sayHelloApplication(final Model model) {
        final String message = HmrcFactory.createForApplicationRestrictedAccess(hmrcCredentials).getAPI(HelloWorldAPI.class).sayHelloApplication();
        model.addAttribute("message", message);
        return render(model);
    }

    @RequestMapping(value = "/sample/hello/user")
    public String sayHelloUser(final Model model) {
        LogFactory.getLog(SampleController.class).debug("Invoking hello user");
        final String message = HmrcFactory.createForUserRestrictedAccess(hmrcCredentials, tokenStore).getAPI(HelloWorldAPI.class).sayHelloUser();
        model.addAttribute("message", message);
        return render(model);
    }

    private String authorizeUrl(final Scope... scopes) {
        return HmrcFactory.createForUserRestrictedAccess(hmrcCredentials, tokenStore).getAuthorizeUrl(scopes).map(URL::toString).orElse("");
    }

    private boolean hasToken() {
        return tokenStore.getToken().isPresent();
    }

}