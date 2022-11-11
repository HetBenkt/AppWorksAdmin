package nl.bos.integration;

import nl.bos.Utils;
import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AuthenticationIntegrationTest {

    @BeforeAll
    static void isSystemUp() {
        Configuration config = new ConfigurationImpl("config_integration.properties");
        AppWorksPlatform awp = AppWorksPlatformImpl.getInstance(config);
        Assumptions.assumeThat(awp.ping()).isTrue();
    }

    @AfterAll
    static void cleanData() {
        if(Utils.artifactFileExists()) {
            Utils.deleteArtifactFile();
        }
    }

    @Test
    void getSamlToken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;

        String samlArtifactId = "";
        if(!Utils.artifactFileExists()) {
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
    void getSamlTokenFromOtdsToken() {
        Authentication authentication = AuthenticationImpl.INSTANCE;
        String otdsTicket = authentication.getOTDSTicket();
        Assertions.assertThat(otdsTicket).isNotEmpty();

        String samlArtifactId = "";
        if(!Utils.artifactFileExists()) {
            samlArtifactId = authentication.getToken(otdsTicket);
            Utils.writeToFile(samlArtifactId);
        } else {
            samlArtifactId = Utils.readFromFile();
        }
        Assertions.assertThat(samlArtifactId).isNotEmpty();
    }
}
