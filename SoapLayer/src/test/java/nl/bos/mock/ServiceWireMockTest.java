package nl.bos.mock;

import nl.bos.exception.GeneralAppException;
import nl.bos.integration.TestIntegrationData;
import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ServiceWireMockTest extends AbstractWireMockTest {

    @Test
    void callGetUserDetails() {
        wireMockAppWorksServer.stubFor(
                post(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetUserDetails))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/xml")
                                .withBody(TestMockData.soapResponseGetUserDetails)));

        Service service = new ServiceImpl();
        String serviceResponse = service.call(TestIntegrationData.soapRequestGetUserDetails);

        wireMockAppWorksServer.verify(
                postRequestedFor(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetUserDetails)));
        Assumptions.assumeThat(serviceResponse).contains("GetUserDetailsResponse");
    }

    @Test
    void callGetSoapProcessors() {
        wireMockAppWorksServer.stubFor(
                post(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetSoapProcessors))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/xml")
                                .withBody(TestMockData.soapResponseGetSoapProcessors)));

        Service service = new ServiceImpl();
        String serviceResponse = service.call(TestIntegrationData.soapRequestGetSoapProcessors);

        wireMockAppWorksServer.verify(
                postRequestedFor(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestGetSoapProcessors)));
        Assumptions.assumeThat(serviceResponse).contains("GetSoapProcessorsResponse");
    }

    @Test
    void callSearchLDAP() {
        wireMockAppWorksServer.stubFor(
                post(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestSearchLDAP))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "text/xml")
                                .withBody(TestMockData.soapResponseSearchLDAP)));

        Service service = new ServiceImpl();
        String serviceResponse = service.call(TestIntegrationData.soapRequestSearchLDAP);
        wireMockAppWorksServer.verify(
                postRequestedFor(urlPathEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp"))
                        .withQueryParam("SAMLart", matching("(.*)"))
                        .withRequestBody(equalToXml(TestIntegrationData.soapRequestSearchLDAP)));
        Assumptions.assumeThat(serviceResponse).contains("SearchLDAPResponse");
    }

    @Test
    void getServiceFailure() {
        wireMockAppWorksServer.resetMappings();

        Service service = new ServiceImpl();
        Exception exception = assertThrows(GeneralAppException.class, () -> service.call(TestIntegrationData.soapRequestGetSoapProcessors));
        Assertions.assertThat(exception.getClass().getSimpleName()).isEqualTo(GeneralAppException.class.getSimpleName());
    }
}