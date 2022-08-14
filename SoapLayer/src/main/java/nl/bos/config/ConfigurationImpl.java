package nl.bos.config;

import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.exception.GeneralAppException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public enum ConfigurationImpl implements Configuration {
    INSTANCE;

    private final Properties properties = new Properties();

    ConfigurationImpl() {
        try (InputStream inStream = AppWorksPlatformImpl.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inStream);
            Logger.getLogger(this.getClass().getName()).info("Config file loaded...");
        } catch (IOException e) {
            throw new GeneralAppException(e);
        }
    }

    @Override
    public Properties getProperties() {
        return properties;
    }
}
