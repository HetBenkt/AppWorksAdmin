package nl.bos.mock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationMockTest {

    @Test
    void getSamlToken() {
        //TODO implementation
        boolean actual = true;
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void getOtdsToken() {
        //TODO implementation
        boolean actual = true;
        Assertions.assertThat(actual).isTrue();
    }
}
