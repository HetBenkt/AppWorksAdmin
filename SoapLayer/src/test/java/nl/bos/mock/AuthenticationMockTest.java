package nl.bos.mock;

import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationMockTest {

    private final TestData testData = TestData.INSTANCE;
    @Mock
    private SOAPConnectionFactory soapConnectionFactoryInstanceMock;
    @Mock
    private CloseableHttpClient closeableHttpClientMock;
    @Mock
    private SOAPConnection soapConnectionMock;
    @Mock
    private CloseableHttpResponse closeableHttpResponseMock;

    @BeforeAll
    static void isSystemUp() {
        AppWorksPlatform awp = AppWorksPlatformImpl.INSTANCE;
        Assumptions.assumeThat(awp.ping()).isFalse();
    }

    @Test
    void getSamlToken() throws SOAPException, IOException {
        MockedStatic<SOAPConnectionFactory> soapConnectionFactoryMock = mockStatic(SOAPConnectionFactory.class);

        soapConnectionFactoryMock.when(SOAPConnectionFactory::newInstance).
                thenReturn(soapConnectionFactoryInstanceMock);
        when(soapConnectionFactoryInstanceMock.createConnection()).
                thenReturn(soapConnectionMock);
        when(soapConnectionMock.call(any(), any())).
                thenReturn(MessageFactory.newInstance().createMessage(null, new ByteArrayInputStream(testData.soapMessage.getBytes())));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getToken()).isNotEmpty();

        verify(soapConnectionMock).call(any(), any());
    }

    @Test
    void getOtdsToken() throws IOException {
        MockedStatic<HttpClients> httpClientsMock = mockStatic(HttpClients.class);

        httpClientsMock.when(HttpClients::createDefault).
                thenReturn(closeableHttpClientMock);
        when(closeableHttpClientMock.execute(any())).
                thenReturn(closeableHttpResponseMock);
        when(closeableHttpResponseMock.getEntity()).
                thenReturn(new StringEntity(testData.jsonMessage, StandardCharsets.UTF_8));

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getOTDSTicket()).isNotEmpty();

        verify(closeableHttpClientMock).execute(any());
    }

    @Test
    void getSamlTokenFromOtdsToken() {
        //TODO implementation
        boolean actual = true;
        Assertions.assertThat(actual).isTrue();
    }
}
