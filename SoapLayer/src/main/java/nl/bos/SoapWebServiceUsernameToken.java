package nl.bos;

import org.w3c.dom.Node;

import javax.xml.namespace.QName;
import javax.xml.soap.*;

public class SoapWebServiceUsernameToken implements SoapWebServiceStrategy {
    private final String url;

    public SoapWebServiceUsernameToken(String url) {
        this.url = url;
    }

    @Override
    public String run() {
        String SAMLArtifactID = "";
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            SOAPMessage soapRequest = createSOAPRequest();
            SOAPMessage soapResponse = soapConnection.call(soapRequest, url);

            // Print the SOAP Response
            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            SOAPBody soapBody = soapResponse.getSOAPBody();
            Node assertionArtifact = soapBody.getElementsByTagName("samlp:AssertionArtifact").item(0);
            SAMLArtifactID = assertionArtifact.getTextContent();
            System.out.println("SAMLArtifactID: " + SAMLArtifactID);

            soapConnection.close();


        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
        }
        return SAMLArtifactID;
    }

    private SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        createSoapEnvelope(soapMessage);
        soapMessage.saveChanges();

        /* Print the request message, just for debugging purposes */
        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

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
        soapUsernameElem.addTextNode("sysadmin");
        SOAPElement soapPasswordElem = soapUsernameTokenElem.addChildElement("Password", namespaceWsse);
        soapPasswordElem.addTextNode("admin");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapRequestElem = soapBody.addChildElement("Request", namespaceSamlp);
        soapRequestElem.addAttribute(new QName("MajorVersion"), "1");
        soapRequestElem.addAttribute(new QName("MinorVersion"), "1");
        SOAPElement soapAuthenticationQueryElem = soapRequestElem.addChildElement("AuthenticationQuery", namespaceSamlp);
        SOAPElement soapSubjectElem = soapAuthenticationQueryElem.addChildElement("Subject", namespaceSaml);
        SOAPElement soapNameIdentifierElem = soapSubjectElem.addChildElement("NameIdentifier", namespaceSaml);
        soapNameIdentifierElem.addAttribute(new QName("Format"), "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified");
        soapNameIdentifierElem.addTextNode("sysadmin");
    }
}
