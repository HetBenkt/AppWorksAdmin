package nl.bos.integration;

import nl.bos.Authentication;
import nl.bos.AuthenticationImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AuthenticationIntegrationTest {

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
