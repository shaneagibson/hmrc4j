package uk.co.epsilontechnologies.hmrc4j;

import uk.co.epsilontechnologies.hmrc4j.core.API;
import uk.co.epsilontechnologies.hmrc4j.core.Hmrc;
import uk.co.epsilontechnologies.hmrc4j.core.HmrcCredentials;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public abstract class AbstractIT {

    private final static Properties config = loadConfig();

    private static final String CLIENT_ID = getConfig("hmrc.credentials.client_id");
    private static final String CLIENT_SECRET = getConfig("hmrc.credentials.client_secret");
    private static final String SERVER_TOKEN = getConfig("hmrc.credentials.server_token");

    protected <T extends API> T getAPI(final Class<T> apiClass) {
        return createHmrc().getAPI(apiClass);
    }

    protected abstract Hmrc createHmrc();

    protected static String getConfig(final String key) {
        return config.getProperty(key);
    }

    protected HmrcCredentials getHmrcCredentials() {
        return new HmrcCredentials(CLIENT_ID, CLIENT_SECRET, SERVER_TOKEN);
    }

    private static Properties loadConfig() {
        try {
            final Properties config = new Properties();
            config.load(new FileReader(new File("src/test/resources/base.properties")));
            config.load(new FileReader(new File("src/test/resources/secure.properties")));
            return config;
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

}