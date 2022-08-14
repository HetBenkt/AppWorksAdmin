package nl.bos.ws.strategy;

import org.w3c.dom.Node;

import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoapWebServiceToken implements SoapWebServiceStrategy {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final String url;

    public SoapWebServiceToken(final String url) {
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

    void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        throw new UnsupportedOperationException();
    }
}
