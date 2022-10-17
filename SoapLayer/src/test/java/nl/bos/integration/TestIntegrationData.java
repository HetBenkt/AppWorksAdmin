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

    final String requestSearchLdap = """
            <SOAP:Envelope xmlns:SOAP="http://schemas.xmlsoap.org/soap/envelope/">
              <SOAP:Body>
                <SearchLDAP xmlns="http://schemas.cordys.com/1.0/ldap">
                  <dn>cn=cordys,cn=defaultInst,o=22.3.com</dn>
                  <scope>2</scope>
                  <filter>&amp;(objectclass=busmethod)(cn=GetUserDetails*)</filter>
                  <sort>ascending</sort>
                  <sortBy />
                  <returnValues>false</returnValues>
                  <return />
                </SearchLDAP>
              </SOAP:Body>
            </SOAP:Envelope>
            """;
}
