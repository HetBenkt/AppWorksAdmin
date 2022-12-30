package nl.bos.mock;

import nl.bos.integration.TestIntegrationData;
import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ServiceWireMockTest extends AbstractWireMockTest {
    private static final TestIntegrationData testData = TestIntegrationData.INSTANCE;

    @Test
    @Disabled("To be implemented!")
    void callGetUserDetails() {
        //TODO Run stub!
        Service service = new ServiceImpl();
        String serviceResponse = service.call(testData.requestGetUserDetails);
        //TODO Verify stub!
        Assumptions.assumeThat(serviceResponse).contains("GetUserDetailsResponse");
    }

    @Test
    @Disabled("To be implemented!")
    void callGetSoapProcessors() {
        //TODO Run stub!
        Service service = new ServiceImpl();
        String serviceResponse = service.call(testData.requestGetSoapProcessors);
        //TODO Verify stub!
        Assumptions.assumeThat(serviceResponse).contains("GetSoapProcessorsResponse");
    }
}