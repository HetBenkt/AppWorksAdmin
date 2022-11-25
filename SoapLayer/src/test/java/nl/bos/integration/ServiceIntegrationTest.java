package nl.bos.integration;

import nl.bos.Utils;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ServiceIntegrationTest {
    private final static TestIntegrationData testData = TestIntegrationData.INSTANCE;

    @BeforeAll
    static void isSystemUp() {
        Configuration config = new ConfigurationImpl("config_integration.properties");
        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance(config);
        Assumptions.assumeThat(awp.ping()).isTrue();
    }

    @AfterAll
    static void cleanData() throws IOException {
        if (Utils.artifactFileExists()) {
            Utils.deleteArtifactFile();
        }
    }

    @Test
    void callGetUserDetails() {
        Service service = new ServiceImpl();
        String serviceResponse = service.call(testData.requestGetUserDetails);
        Assumptions.assumeThat(serviceResponse).contains("GetUserDetailsResponse");
    }

    @Test
    void callGetSoapProcessors() {
        Service service = new ServiceImpl();
        String serviceResponse = service.call(testData.requestGetSoapProcessors);
        Assumptions.assumeThat(serviceResponse).contains("GetSoapProcessorsResponse");
    }
}