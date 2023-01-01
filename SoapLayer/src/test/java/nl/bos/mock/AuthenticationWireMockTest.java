package nl.bos.mock;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import nl.bos.Utils;
import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest
class AuthenticationWireMockTest extends AbstractWireMockTest {

    @Test
    void getSamlToken() throws IOException {
        wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseSamlToken)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        String samlArtifactId;

        if (!Utils.artifactFileExists()) {
            samlArtifactId = authentication.getToken();
            wireMockAppWorksServer.verify(postRequestedFor(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")));
            Utils.writeToFile(samlArtifactId);
        } else {
            samlArtifactId = Utils.readFromFile();
        }

        Assertions.assertThat(samlArtifactId).isNotEmpty();
    }

    @Test
    void getOtdsToken() {
        wireMockOtdsServer.stubFor(post(urlEqualTo("/otdsws/rest/authentication/credentials")).willReturn(aResponse().withBody(TestMockData.jsonResponseOtdsAuthentication)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getOTDSTicket()).isNotEmpty();

        wireMockOtdsServer.verify(postRequestedFor(urlEqualTo("/otdsws/rest/authentication/credentials")));
    }

    @Test
    void getSamlTokenFromOtdsToken() throws IOException {
        wireMockOtdsServer.stubFor(post(urlEqualTo("/otdsws/rest/authentication/credentials")).willReturn(aResponse().withBody(TestMockData.jsonResponseOtdsAuthentication)));
        wireMockAppWorksServer.stubFor(post(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")).willReturn(aResponse().withHeader("Content-Type", "text/xml").withBody(TestMockData.soapResponseSamlToken)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        String otdsTicket = authentication.getOTDSTicket();
        Assertions.assertThat(otdsTicket).isNotEmpty();
        String samlArtifactId;

        if (!Utils.artifactFileExists()) {
            samlArtifactId = authentication.getToken(otdsTicket);
            wireMockOtdsServer.verify(postRequestedFor(urlEqualTo("/otdsws/rest/authentication/credentials")));
            wireMockAppWorksServer.verify(postRequestedFor(urlEqualTo("/home/appworks_tips/com.eibus.web.soap.Gateway.wcp")));
            Utils.writeToFile(samlArtifactId);
        } else {
            samlArtifactId = Utils.readFromFile();
        }

        Assertions.assertThat(samlArtifactId).isNotEmpty();
    }
}
