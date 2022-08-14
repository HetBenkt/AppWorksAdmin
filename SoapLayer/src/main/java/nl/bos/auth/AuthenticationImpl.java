package nl.bos.auth;

import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;

public enum AuthenticationImpl implements Authentication {
    INSTANCE;

    private final Configuration config = ConfigurationImpl.INSTANCE;

    public String getToken() {
        AuthenticationService authenticationService = new AuthenticationServiceImpl(config.getProperties().getProperty("gateway_url"));
        return authenticationService.getToken();
    }

    @Override
    public String getOTDSTicket() {
        AuthenticationService authenticationService = new AuthenticationServiceImpl(config.getProperties().getProperty("otds_url"));
        return authenticationService.getOTDSTicket();
    }

    @Override
    public String getToken(String otdsTicket) {
        AuthenticationService authenticationService = new AuthenticationServiceImpl(config.getProperties().getProperty("gateway_url"));
        return authenticationService.getToken(otdsTicket);
    }
}
