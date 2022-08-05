package nl.bos.integration;

import nl.bos.Authentication;
import nl.bos.AuthenticationImpl;
import nl.bos.AppWorksPlatform;
import nl.bos.AppWorksPlatformImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AuthenticationIntegrationTest {

    @BeforeAll
    static void isSystemUp() {
        AppWorksPlatform awp = AppWorksPlatformImpl.INSTANCE;
        Assumptions.assumeThat(awp.ping()).isTrue();
    }

    @Test
    void getSAMLtoken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getToken()).isNotEmpty();
    }

    @Test
    void getOTDStoken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getOTDSToken()).isNotEmpty();
    }
}
