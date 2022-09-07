package nl.bos.mock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@WireMockTest
class AuthenticationWireMockTest {

    private final TestData testData = TestData.INSTANCE;
    private static final WireMockServer wireMockAppWorksServer = new WireMockServer(); //default is http://localhost:8080
    private static final WireMockServer wireMockOtdsServer = new WireMockServer(options().port(8181)); //Better use dynamicPort(), but we read a props-file!

    @BeforeAll
    static void isSystemUp() {
        wireMockAppWorksServer.start();
        wireMockOtdsServer.start();

        wireMockAppWorksServer.stubFor(get(urlEqualTo("/home/system/app/mp/health/ready")).willReturn(aResponse()));

        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance("config_mock.properties");
        Assumptions.assumeThat(awp.ping()).isTrue();

        wireMockAppWorksServer.verify(getRequestedFor(urlEqualTo("/home/system/app/mp/health/ready")));
    }

    @AfterAll
    static void shutdownWireMock() {
        wireMockAppWorksServer.stop();
        wireMockOtdsServer.stop();
    }

    @Test
    void getSamlToken() {
        wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(testData.soapMessage)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getToken()).isNotEmpty();

        wireMockAppWorksServer.verify(postRequestedFor(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")));
    }

    @Test
    void getOtdsToken() {
        wireMockOtdsServer.stubFor(post(urlEqualTo("/otdsws/rest/authentication/credentials")).willReturn(aResponse().withBody(testData.jsonMessage)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getOTDSTicket()).isNotEmpty();

        wireMockOtdsServer.verify(postRequestedFor(urlEqualTo("/otdsws/rest/authentication/credentials")));
    }

    @Test
    void getSamlTokenFromOtdsToken() {
        wireMockOtdsServer.stubFor(post(urlEqualTo("/otdsws/rest/authentication/credentials")).willReturn(aResponse().withBody(testData.jsonMessage)));
        wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(testData.soapMessage)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        String otdsTicket = authentication.getOTDSTicket();
        Assertions.assertThat(otdsTicket).isNotEmpty();
        Assertions.assertThat(authentication.getToken(otdsTicket)).isNotEmpty();

        wireMockOtdsServer.verify(postRequestedFor(urlEqualTo("/otdsws/rest/authentication/credentials")));
        wireMockAppWorksServer.verify(postRequestedFor(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")));
    }
}
