package nl.bos.mock;

import nl.bos.auth.Authentication;
import nl.bos.auth.AuthenticationImpl;
import nl.bos.awp.AppWorksPlatform;
import nl.bos.awp.AppWorksPlatformImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationMockTest {

    @BeforeAll
    static void isSystemUp() {
        AppWorksPlatform awp = AppWorksPlatformImpl.INSTANCE;
        Assumptions.assumeThat(awp.ping()).isFalse();
    }

    @Test
    void getSamlToken() throws SOAPException, IOException {
        MockedStatic soapConnectionFactoryMock = mockStatic(SOAPConnectionFactory.class);
        SOAPConnectionFactory soapConnectionFactoryInstanceMock = mock(SOAPConnectionFactory.class);
        soapConnectionFactoryMock.when(SOAPConnectionFactory::newInstance).thenReturn(soapConnectionFactoryInstanceMock);
        SOAPConnection soapConnectionMock = mock(SOAPConnection.class);
        when(soapConnectionFactoryInstanceMock.createConnection()).thenReturn(soapConnectionMock);

        String soapMessage = """
                <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:wsse="urn:api.ecm.opentext.com">
                	<SOAP-ENV:Header xmlns:wsse="urn:api.ecm.opentext.com" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                		<header xmlns:wsse="urn:api.ecm.opentext.com" xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/" xmlns="http://schemas.cordys.com/General/1.0/">
                			<msg-id>080027c2-550f-a1ed-8700-cc06a8f8261d</msg-id>
                			<messageoptions noreply="true"/>
                		</header>
                	</SOAP-ENV:Header>
                	<SOAP-ENV:Body>
                		<samlp:Response xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" IssueInstant="2022-08-14T18:59:13.590Z" MajorVersion="1" MinorVersion="1" ResponseID="A080027c2-550f-a1ed-8700-cc06be6a261d">
                			<Signature xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns="http://www.w3.org/2000/09/xmldsig#">
                				<SignedInfo>
                					<CanonicalizationMethod Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
                					<SignatureMethod Algorithm="http://www.w3.org/2001/04/xmldsig-more#rsa-sha256"/>
                					<Reference URI="#A080027c2-550f-a1ed-8700-cc06c236a61d">
                						<Transforms>
                							<Transform Algorithm="http://www.w3.org/2000/09/xmldsig#enveloped-signature"/>
                							<Transform Algorithm="http://www.w3.org/2001/10/xml-exc-c14n#"/>
                						</Transforms>
                						<DigestMethod Algorithm="http://www.w3.org/2001/04/xmlenc#sha256"/>
                						<DigestValue>JvZcy2WwXwmWUZG6ug5XwAGBhTaZsppEOI88ngiOMmk=</DigestValue>
                					</Reference>
                				</SignedInfo>
                				<SignatureValue>XdJ+Op8BId3DyVgnyQqEZuiotqv+AJH3lLQuJNnt8C7B2Hp70j04JqZpi06i9yBJlABORWFiF5mz
                					/MW0ebx7lQlqr81O0NzyqhH92HHU1kkKUVQY92DQhtd5Y5nHQkM8OkCIo+qfEMH42H90bIZku+FZ
                					N69XFI+oAUAsYSY1iL6Rp4bdaE08/eY+ZEFLeFox/i41i08c6Y4Ia9QHYuLBIr1ANI/4Ii68/FZu
                					fbXtGds5GeqDVhnRu4XpPw5zTl7ENlSnygeaboUnsOcSWTb/XaknWKxuOkosbuVRqmDtQ9/a3rcn
                					EKqm89pD9gITF4CvGXo2w/gghE92D0WH7aZjEQ==</SignatureValue>
                			</Signature>
                			<samlp:Status xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol">
                				<samlp:StatusCode Value="samlp:Success"/>
                			</samlp:Status>
                			<saml:Assertion xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol" xmlns:saml="urn:oasis:names:tc:SAML:1.0:assertion" AssertionID="A080027c2-550f-a1ed-8700-cc06c236a61d" IssueInstant="2022-08-14T18:59:13.591Z" Issuer="https://www.cordys.com/SSO" MajorVersion="1" MinorVersion="1">
                				<saml:Conditions NotBefore="2022-08-14T18:54:13.590Z" NotOnOrAfter="2022-08-15T02:59:13.590Z"/>
                				<saml:AuthenticationStatement AuthenticationInstant="2022-08-14T18:59:13.590Z" AuthenticationMethod="urn:oasis:names:tc:SAML:1.0:am:password">
                					<saml:Subject>
                						<saml:NameIdentifier>anonymous</saml:NameIdentifier>
                					</saml:Subject>
                				</saml:AuthenticationStatement>
                			</saml:Assertion>
                			<samlp:AssertionArtifact xmlns:samlp="urn:oasis:names:tc:SAML:1.0:protocol">e0pBVkEtQUVTL0dDTS9Ob1BhZGRpbmd9WWGZ9li84Pl0ZLM4LqD57o8Q1lUNUlfFxq8t+1kg7pbbtnEe4zaDioNaAELZioW3Flz7G0e4hHeR/Mv2/ibul6rCzX5FoCs0BciHSbOc5M7dfcY2fC/Np8dRefcVWQZKW58u</samlp:AssertionArtifact>
                		</samlp:Response>
                	</SOAP-ENV:Body>
                </SOAP-ENV:Envelope>
                """;
        InputStream is = new ByteArrayInputStream(soapMessage.getBytes());
        SOAPMessage soapResponse = MessageFactory.newInstance().createMessage(null, is);

        when(soapConnectionMock.call(any(), any())).thenReturn(soapResponse);

        Authentication authentication = AuthenticationImpl.INSTANCE;
        Assertions.assertThat(authentication.getToken()).isNotEmpty();

        verify(soapConnectionMock).call(any(), any());
    }

    @Test
    void getOtdsToken() {
        //TODO implementation
        boolean actual = true;
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void getSamlTokenFromOtdsToken() {
        //TODO implementation
        boolean actual = true;
        Assertions.assertThat(actual).isTrue();
    }
}
