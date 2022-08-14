package nl.bos.awp;

import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;

public enum AppWorksPlatformImpl implements AppWorksPlatform {
    INSTANCE;

    private final Configuration config = ConfigurationImpl.INSTANCE;

    public boolean ping() {
        AppWorksPlatformService awpService = new AppWorksPlatformServiceImpl(config.getProperties().getProperty("health_url"));
        return awpService.ping();
    }
}
