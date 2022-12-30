package nl.bos.integration;

import nl.bos.Utils;
import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class AuthenticationIntegrationTest extends AbstractIntegrationTest {

    @Test
    void getSamlToken() throws IOException {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        String samlArtifactId;

        if (!Utils.artifactFileExists()) {
            samlArtifactId = authentication.getToken();
            Utils.writeToFile(samlArtifactId);
        } else {
            samlArtifactId = Utils.readFromFile();
        }

        Assertions.assertThat(samlArtifactId).isNotEmpty();
    }

    @Test
    void getOtdsToken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getOTDSTicket()).isNotEmpty();
    }

    @Test
    void getSamlTokenFromOtdsToken() throws IOException {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        String otdsTicket = authentication.getOTDSTicket();
        Assertions.assertThat(otdsTicket).isNotEmpty();
        String samlArtifactId;

        if (!Utils.artifactFileExists()) {
            samlArtifactId = authentication.getToken(otdsTicket);
            Utils.writeToFile(samlArtifactId);
        } else {
            samlArtifactId = Utils.readFromFile();
        }

        Assertions.assertThat(samlArtifactId).isNotEmpty();
    }
}
