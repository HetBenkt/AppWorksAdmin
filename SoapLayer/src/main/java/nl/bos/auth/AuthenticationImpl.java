package nl.bos.auth;

public enum AuthenticationImpl implements Authentication {
    INSTANCE;

    public String getToken() {
        String gatewayURL = "http://192.168.56.110:8080/home/appworks_tips/com.eibus.web.soap.Gateway.wcp";
        AuthenticationService authenticationService = new AuthenticationServiceImpl(gatewayURL);
        return authenticationService.getToken();
    }

    @Override
    public String getOTDSTicket() {
        String otdsUrl = "http://192.168.56.110:8181/otdsws/rest/authentication/credentials";
        AuthenticationService authenticationService = new AuthenticationServiceImpl(otdsUrl);
        return authenticationService.getOTDSTicket();
    }

    @Override
    public String getToken(String otdsTicket) {
        String gatewayURL = "http://192.168.56.110:8080/home/appworks_tips/com.eibus.web.soap.Gateway.wcp";
        AuthenticationService authenticationService = new AuthenticationServiceImpl(gatewayURL);
        return authenticationService.getToken(otdsTicket);
    }
}
