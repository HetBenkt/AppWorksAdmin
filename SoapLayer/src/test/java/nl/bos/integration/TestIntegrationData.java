package nl.bos.integration;

public enum TestIntegrationData {
    INSTANCE;

    //TODO think on how to pass parameters
    final String requestGetUserDetails = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP:Body>
                    <GetUserDetails xmlns="http://schemas.cordys.com/notification/workflow/1.0">
                    </GetUserDetails>
                </SOAP:Body>
            </SOAP:Envelope>
            """;

    final String requestGetSoapProcessors = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
              <SOAP:Body>
                <GetSoapProcessors xmlns="http://schemas.cordys.com/1.0/ldap">
                  <dn>o=system,cn=cordys,cn=defaultInst,o=22.3.com</dn>
                  <sort>ascending</sort>
                </GetSoapProcessors>
              </SOAP:Body>
            </SOAP:Envelope>
            """;
}
