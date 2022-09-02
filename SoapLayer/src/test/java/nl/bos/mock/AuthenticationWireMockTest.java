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
import org.junit.jupiter.api.Disabled;
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
    @Disabled
    void getSamlToken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getToken()).isNotEmpty();
        //verify(soapConnectionMock).call(any(), any());
    }

    @Test
    void getOtdsToken() {
        WireMockServer wireMockServer = new WireMockServer(options().port(8181));
        wireMockServer.start();
        configureFor("localhost", 8181);
        stubFor(post(urlEqualTo("/otdsws/rest/authentication/credentials")).willReturn(aResponse().withBody(testData.jsonMessage)));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getOTDSTicket()).isNotEmpty();

        verify(postRequestedFor(urlEqualTo("/otdsws/rest/authentication/credentials")));
        wireMockServer.stop();
    }

    @Test
    @Disabled
    void getSamlTokenFromOtdsToken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        String otdsTicket = authentication.getOTDSTicket();
        Assertions.assertThat(otdsTicket).isNotEmpty();
        Assertions.assertThat(authentication.getToken(otdsTicket)).isNotEmpty();
//        verify(closeableHttpClientMock).execute(any());
//        verify(soapConnectionMock).call(any(), any());
    }
}
