package nl.bos.config;

import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.exception.GeneralAppException;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigurationImpl implements Configuration {
    private static final Properties properties = new Properties();
    private static Configuration configuration;

    private ConfigurationImpl(String fileName) {
        try (InputStream inStream = AppWorksPlatformImpl.class.getClassLoader().getResourceAsStream(fileName)) {
            properties.load(inStream);
            String message = MessageFormat.format("Config file ''{0}'' loaded...", fileName);
            Logger.getLogger(Configuration.class.getName()).info(message);
        } catch (IOException e) {
            throw new GeneralAppException(e);
        }
    }

    public static Configuration getInstance(String fileName) {
        if (configuration == null) {
            configuration = new ConfigurationImpl(fileName);
        }
        return configuration;
    }

    public static Configuration getInstance() {
        return getInstance("config.properties");
    }

    @Override
    public Properties getProperties() {
        return properties;
    }
}
