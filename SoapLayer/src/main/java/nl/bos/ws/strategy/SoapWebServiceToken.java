package nl.bos.ws.strategy;

import jakarta.xml.soap.*;
import nl.bos.awp.AppWorksPlatformImpl;
import nl.bos.config.Configuration;
import nl.bos.exception.GeneralAppException;
import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class SoapWebServiceToken implements SoapWebServiceStrategy {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final String url;
    private static final Configuration config = AppWorksPlatformImpl.getInstance().getConfig();

    public SoapWebServiceToken(final String url) {
        this.url = url;
    }

    @Override
    public String run() {
        String samlArtifactId;
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
            //TODO format the XML output
            logger.info(msg);

            SOAPBody soapBody = soapResponse.getSOAPBody();
            Node assertionArtifact = soapBody.getElementsByTagName("samlp:AssertionArtifact").item(0);
            samlArtifactId = assertionArtifact.getTextContent(); //TODO Save in in a file (via Configuration class?) and check if available/expired??
            String msgFormat = String.format("samlArtifactId: %s", samlArtifactId);
            logger.info(msgFormat);

            soapConnection.close();
        } catch (SOAPException | IOException e) {
            throw new GeneralAppException(e);
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

    void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        throw new UnsupportedOperationException();
    }

    void buildSoapBody(String namespaceSamlp, String namespaceSaml, SOAPEnvelope envelope) throws SOAPException {
        createSoapElement(namespaceSamlp, namespaceSaml, envelope);
    }

    void buildSoapBodyWithUser(String namespaceSamlp, String namespaceSaml, SOAPEnvelope envelope) throws SOAPException {
        SOAPElement soapNameIdentifierElem = createSoapElement(namespaceSamlp, namespaceSaml, envelope);
        soapNameIdentifierElem.addTextNode(config.getProperties().getProperty("admin_username"));
    }

    private SOAPElement createSoapElement(String namespaceSamlp, String namespaceSaml, SOAPEnvelope envelope) throws SOAPException {
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapRequestElem = soapBody.addChildElement("Request", namespaceSamlp);
        soapRequestElem.addAttribute(new QName("MajorVersion"), "1");
        soapRequestElem.addAttribute(new QName("MinorVersion"), "1");
        SOAPElement soapAuthenticationQueryElem = soapRequestElem.addChildElement("AuthenticationQuery", namespaceSamlp);
        SOAPElement soapSubjectElem = soapAuthenticationQueryElem.addChildElement("Subject", namespaceSaml);
        SOAPElement soapNameIdentifierElem = soapSubjectElem.addChildElement("NameIdentifier", namespaceSaml);
        soapNameIdentifierElem.addAttribute(new QName("Format"), "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
        return soapNameIdentifierElem;
    }
}
