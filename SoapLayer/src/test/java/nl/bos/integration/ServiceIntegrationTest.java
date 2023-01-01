package nl.bos.integration;

import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;

class ServiceIntegrationTest extends AbstractIntegrationTest {

    @Test
    void callGetUserDetails() {
        Service service = new ServiceImpl();
        String serviceResponse = service.call(TestIntegrationData.soapRequestGetUserDetails);
        Assumptions.assumeThat(serviceResponse).contains("GetUserDetailsResponse");
    }

    @Test
    void callGetSoapProcessors() {
        Service service = new ServiceImpl();
        String serviceResponse = service.call(TestIntegrationData.soapRequestGetSoapProcessors);
        Assumptions.assumeThat(serviceResponse).contains("GetSoapProcessorsResponse");
    }

    @Test
    void callSearchLDAP() {
        Service service = new ServiceImpl();
        String serviceResponse = service.call(TestIntegrationData.soapRequestSearchLDAP);
        Assumptions.assumeThat(serviceResponse).contains("SearchLDAPResponse");
    }
}