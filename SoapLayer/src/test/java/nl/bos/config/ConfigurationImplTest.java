package nl.bos.config;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;


class ConfigurationImplTest {

    @Test
    void getProperties() {
        Configuration config = new ConfigurationImpl();
        Properties properties = config.getProperties();

        Assertions.assertThat(properties.getProperty("health_url")).isNotEmpty();
    }

    @Test
    void getPropertiesFailure() {
        Configuration config = new ConfigurationImpl("");
        Properties properties = config.getProperties();

        Assertions.assertThat(properties.getProperty("")).isNull();
    }
}