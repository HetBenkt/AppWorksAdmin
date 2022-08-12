package nl.bos.awp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum AppWorksPlatformImpl implements AppWorksPlatform {
    INSTANCE;
    private static final Properties properties = new Properties();

    static {
        try (InputStream inStream = AppWorksPlatformImpl.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading config.properties");
        }
    }

    public boolean ping() {
        AppWorksPlatformService awpService = new AppWorksPlatformServiceImpl(properties.getProperty("health_url"));
        return awpService.ping();
    }
}
