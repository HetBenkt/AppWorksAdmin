package nl.bos.integration;

import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AuthenticationIntegrationTest {

    @BeforeAll
    static void isSystemUp() {
        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance("config_integration.properties");
        Assumptions.assumeThat(awp.ping()).isTrue();
    }

    @Test
    void getSamlToken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getToken()).isNotEmpty();
    }

    @Test
    void getOtdsToken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getOTDSTicket()).isNotEmpty();
    }

    @Test
    void getSamlTokenFromOtdsToken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        String otdsTicket = authentication.getOTDSTicket();
        Assertions.assertThat(otdsTicket).isNotEmpty();
        Assertions.assertThat(authentication.getToken(otdsTicket)).isNotEmpty();
    }
}
