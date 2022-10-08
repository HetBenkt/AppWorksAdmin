package nl.bos.integration;

import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ServiceIntegrationTest {

    @BeforeAll
    static void isSystemUp() {
        Configuration config = new ConfigurationImpl("config_integration.properties");
        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance(config);
        Assumptions.assumeThat(awp.ping()).isTrue();
    }

    @Test
    void call() {
        String body = """
                <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                    <SOAP:Body>
                        <GetUserDetails xmlns="http://schemas.cordys.com/notification/workflow/1.0">
                        </GetUserDetails>
                    </SOAP:Body>
                </SOAP:Envelope>
                """;

        Service service = new ServiceImpl();
        String serviceResponse = service.call(body);
        Assumptions.assumeThat(serviceResponse).contains("GetUserDetailsResponse");
    }
}