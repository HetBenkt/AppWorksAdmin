package nl.bos.config;

import nl.bos.awp.AppWorksPlatformImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum ConfigurationImpl implements Configuration {
    INSTANCE;

    private Properties properties = new Properties();

    ConfigurationImpl() {
        try (InputStream inStream = AppWorksPlatformImpl.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(inStream);
            System.out.println("Config file loaded...");
        } catch (IOException e) {
            throw new RuntimeException("Error reading config.properties");
        }
    }

    @Override
    public Properties getProperties() {
        return properties;
    }
}
