package nl.bos.ws.strategy;

import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoapWebServiceUsernameToken implements SoapWebServiceStrategy {
    private final String url;

    private static final Configuration config = ConfigurationImpl.INSTANCE;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public SoapWebServiceUsernameToken(final String url) {
        this.url = url;
    }

    @Override
    public String run() {
        String samlArtifactId = "";
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapRequest = createSOAPRequest();
            SOAPMessage soapResponse = soapConnection.call(soapRequest, url);

            // Print the SOAP Response
            logger.info("Response SOAP Message:");
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            soapResponse.writeTo(bout);
            String msg = bout.toString(StandardCharsets.UTF_8);
            logger.info(msg);

            SOAPBody soapBody = soapResponse.getSOAPBody();
            Node assertionArtifact = soapBody.getElementsByTagName("samlp:AssertionArtifact").item(0);
            samlArtifactId = assertionArtifact.getTextContent();
            if (logger.getLevel() == Level.INFO) {
                String msgFormat = String.format("samlArtifactId: %s", samlArtifactId);
                logger.info(msgFormat);
            }

            soapConnection.close();


        } catch (SOAPException | IOException e) {
            logger.log(Level.SEVERE, "Error occurred while sending SOAP Request to Server!", e);
        }
        return samlArtifactId;
    }

    private SOAPMessage createSOAPRequest() throws SOAPException, IOException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        createSoapEnvelope(soapMessage);
        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        logger.info("Request SOAP Message:");
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        soapMessage.writeTo(bout);
        String msg = bout.toString(StandardCharsets.UTF_8);
        logger.info(msg);

        return soapMessage;
    }

    private void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String namespaceSamlp = "samlp";
        String namespaceSamlpURI = "urn:oasis:names:tc:SAML:1.0:protocol";
        String namespaceSaml = "saml";
        String namespaceSamlURI = "urn:oasis:names:tc:SAML:1.0:assertion";
        String namespaceWsse = "wsse";
        String namespaceWsseURI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(namespaceSamlp, namespaceSamlpURI);
        envelope.addNamespaceDeclaration(namespaceSaml, namespaceSamlURI);
        envelope.addNamespaceDeclaration(namespaceWsse, namespaceWsseURI);

        // SOAP Header
        SOAPHeader soapHeader = soapMessage.getSOAPHeader();
        SOAPElement soapSecurityElem = soapHeader.addChildElement("Security", namespaceWsse);
        SOAPElement soapUsernameTokenElem = soapSecurityElem.addChildElement("UsernameToken", namespaceWsse);
        SOAPElement soapUsernameElem = soapUsernameTokenElem.addChildElement("Username", namespaceWsse);
        soapUsernameElem.addTextNode(config.getProperties().getProperty("admin_username"));
        SOAPElement soapPasswordElem = soapUsernameTokenElem.addChildElement("Password", namespaceWsse);
        soapPasswordElem.addTextNode(config.getProperties().getProperty("admin_password"));

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapRequestElem = soapBody.addChildElement("Request", namespaceSamlp);
        soapRequestElem.addAttribute(new QName("MajorVersion"), "1");
        soapRequestElem.addAttribute(new QName("MinorVersion"), "1");
        SOAPElement soapAuthenticationQueryElem = soapRequestElem.addChildElement("AuthenticationQuery", namespaceSamlp);
        SOAPElement soapSubjectElem = soapAuthenticationQueryElem.addChildElement("Subject", namespaceSaml);
        SOAPElement soapNameIdentifierElem = soapSubjectElem.addChildElement("NameIdentifier", namespaceSaml);
        soapNameIdentifierElem.addAttribute(new QName("Format"), "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
        soapNameIdentifierElem.addTextNode(config.getProperties().getProperty("admin_username"));
    }
}
