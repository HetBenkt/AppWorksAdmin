package nl.bos.awp;

import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;

public class AppWorksPlatformImpl implements AppWorksPlatform {
    private static AppWorksPlatform appWorksPlatform;
    private final Configuration config;

    private AppWorksPlatformImpl(String fileName) {
        config = ConfigurationImpl.getInstance(fileName);
    }

    public static AppWorksPlatform getInstance(String fileName) {
        if (appWorksPlatform == null) {
            appWorksPlatform = new AppWorksPlatformImpl(fileName);
        }
        return appWorksPlatform;
    }

    public static AppWorksPlatform getInstance() {
        return getInstance("config.properties");
    }

    @Override
    public boolean ping() {
        AppWorksPlatformService awpService = new AppWorksPlatformServiceImpl(config.getProperties().getProperty("health_url"));
        return awpService.ping();
    }
}
