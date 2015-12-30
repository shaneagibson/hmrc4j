package uk.co.epsilontechnologies.hmrc4j.sample.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;
import uk.co.epsilontechnologies.hmrc4j.core.oauth20.TokenStore;
import uk.co.epsilontechnologies.hmrc4j.sample.auth.InMemoryTokenStore;
import uk.co.epsilontechnologies.hmrc4j.sample.auth.servlet.SampleAuthorizeRedirectServlet;

import java.util.UUID;

@Configuration
public class AppConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setLocations(
                new ClassPathResource("config/base.properties"),
                new ClassPathResource("config/secure.properties"));
        return propertySourcesPlaceholderConfigurer;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(final HmrcCredentials hmrcCredentials, final TokenStore tokenStore) {
        return new ServletRegistrationBean(
                new SampleAuthorizeRedirectServlet(hmrcCredentials, tokenStore),
                "/hmrc4j/sample/oauth20/redirect");
    }

    @Bean
    public TokenStore tokenStore(@Value("${base.url}") final String baseUrl) {
        final TokenStore tokenStore = new InMemoryTokenStore();
        tokenStore.setRedirectUri(String.format("%s/hmrc4j/sample/oauth20/redirect", baseUrl));
        tokenStore.setState(UUID.randomUUID().toString());
        return tokenStore;
    }

    @Bean
    public HmrcCredentials hmrcCredentials(
            @Value("${hmrc.credentials.client_id}") final String clientId,
            @Value("${hmrc.credentials.client_secret}") final String clientSecret,
            @Value("${hmrc.credentials.server_token}") final String serverToken) {
        return new HmrcCredentials(clientId, clientSecret, serverToken);
    }

}