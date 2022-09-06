package nl.bos.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@WireMockTest
class AuthenticationWireMockTest {

    private final TestData testData = TestData.INSTANCE;

    @BeforeAll
    static void isSystemUp() {
        AppWorksPlatform awp = AppWorksPlatformImpl.INSTANCE;
        Assumptions.assumeThat(awp.ping()).isFalse();
    }

    @Test
    void getSamlToken() {
        WireMockServer wireMockServer = new WireMockServer(); //default is http://localhost:8080
        wireMockServer.start();
        wireMockServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(testData.soapMessage)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getToken()).isNotEmpty();

        wireMockServer.verify(postRequestedFor(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")));
        wireMockServer.stop();
    }

    @Test
    void getOtdsToken() {
        WireMockServer wireMockServer = new WireMockServer(options().port(8181)); //Better use dynamicPort(), but we read a props-file!
        wireMockServer.start();
        wireMockServer.stubFor(post(urlEqualTo("/otdsws/rest/authentication/credentials")).willReturn(aResponse().withBody(testData.jsonMessage)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getOTDSTicket()).isNotEmpty();

        wireMockServer.verify(postRequestedFor(urlEqualTo("/otdsws/rest/authentication/credentials")));
        wireMockServer.stop();
    }

    @Test
    void getSamlTokenFromOtdsToken() {
        //TODO Reduce duplicate code
        WireMockServer wireMockRestServer = new WireMockServer(options().port(8181)); //Better use dynamicPort(), but we read a props-file!
        wireMockRestServer.start();
        wireMockRestServer.stubFor(post(urlEqualTo("/otdsws/rest/authentication/credentials")).willReturn(aResponse().withBody(testData.jsonMessage)));
        WireMockServer wireMockSoapServer = new WireMockServer(); //default is http://localhost:8080
        wireMockSoapServer.start();
        wireMockSoapServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(testData.soapMessage)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        String otdsTicket = authentication.getOTDSTicket();
        Assertions.assertThat(otdsTicket).isNotEmpty();
        Assertions.assertThat(authentication.getToken(otdsTicket)).isNotEmpty();

        wireMockRestServer.verify(postRequestedFor(urlEqualTo("/otdsws/rest/authentication/credentials")));
        wireMockSoapServer.verify(postRequestedFor(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")));
        wireMockRestServer.stop();
        wireMockSoapServer.stop();
    }
}
