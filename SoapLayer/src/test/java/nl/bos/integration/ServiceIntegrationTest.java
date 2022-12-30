package nl.bos.integration;

import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;

class ServiceIntegrationTest extends AbstractIntegrationTest {

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