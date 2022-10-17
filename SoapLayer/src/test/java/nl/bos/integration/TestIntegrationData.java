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
}
