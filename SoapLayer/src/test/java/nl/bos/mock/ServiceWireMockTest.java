package nl.bos.mock;

import nl.bos.integration.TestIntegrationData;
import nl.bos.operation.Service;
import nl.bos.operation.ServiceImpl;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

class ServiceWireMockTest extends AbstractWireMockTest {

    @Test
    @Disabled("To be implemented!")
    void callGetUserDetails() {
        wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).withRequestBody(equalToXml(TestMockData.soapRequestSamlToken)).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseSamlToken)));
        wireMockAppWorksServer.stubFor(post(urlMatching("(\\/home\\/appworks_tips\\/com\\.eibus\\.web\\.soap\\.Gateway\\.wcp\\?SAMLart=.*)")).withRequestBody(equalToXml(TestIntegrationData.soapRequestGetUserDetails)).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseGetUserDetails)));
        //TODO this should work too: wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).withQueryParam("SAMLart", matching("^(.*)$")).withRequestBody(equalToXml(TestIntegrationData.soapRequestGetUserDetails)).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseGetUserDetails)));

        Service service = new ServiceImpl();
        String serviceResponse = service.call(TestIntegrationData.soapRequestGetUserDetails);
        //TODO Verify stub!
        Assumptions.assumeThat(serviceResponse).contains("GetUserDetailsResponse");
    }

    @Test
    @Disabled("To be implemented!")
    void callGetSoapProcessors() {
        wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseSamlToken)));
        //TODO stub with request data
        // wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseGetSoapProcessors)));

        Service service = new ServiceImpl();
        String serviceResponse = service.call(TestIntegrationData.soapRequestGetSoapProcessors);
        //TODO Verify stub!
        Assumptions.assumeThat(serviceResponse).contains("GetSoapProcessorsResponse");
    }
}