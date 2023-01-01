package nl.bos.integration;

public enum TestIntegrationData {
    ;

    //TODO think on how to pass parameters
    public static final String soapRequestGetUserDetails = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
                <SOAP:Body>
                    <GetUserDetails xmlns="http://schemas.cordys.com/notification/workflow/1.0">
                    </GetUserDetails>
                </SOAP:Body>
            </SOAP:Envelope>
            """;

    public static final String soapRequestGetSoapProcessors = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
              <SOAP:Body>
                <GetSoapProcessors xmlns="http://schemas.cordys.com/1.0/ldap">
                  <dn>o=system,cn=cordys,cn=defaultInst,o=22.3.com</dn>
                  <sort>ascending</sort>
                </GetSoapProcessors>
              </SOAP:Body>
            </SOAP:Envelope>
            """;

    public static final String soapRequestSearchLDAP = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
              <SOAP:Body>
                <SearchLDAP xmlns="http://schemas.cordys.com/1.0/ldap">
                  <dn>cn=cordys,cn=defaultInst,o=22.3.com</dn>
                  <scope>2</scope>
                  <filter>&amp;(objectclass=busmethod)(cn=*GetSoapProcessor*)</filter>
                  <sort>ascending</sort>
                </SearchLDAP>
              </SOAP:Body>
            </SOAP:Envelope>
            """;
}
