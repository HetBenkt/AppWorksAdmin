package nl.bos;

public class AuthenticationServiceImpl implements AuthenticationService {
    private final String url;

    public AuthenticationServiceImpl(final String url) {
        this.url = url;
    }

    @Override
    public String getToken() {
        SoapWebServiceContext soapWebServiceContext = new SoapWebServiceContext(new SoapWebServiceUsernameToken(url));
        return soapWebServiceContext.execute();
    }

    @Override
    public String getOTDSTicket() {
        SoapWebServiceContext soapWebServiceContext = new SoapWebServiceContext(new RestWebServiceOtdsTicket(url));
        return soapWebServiceContext.execute();
    }

    @Override
    public String getToken(String otdsTicket) {
        SoapWebServiceContext soapWebServiceContext = new SoapWebServiceContext(new SoapWebServiceAuthenticationToken(url, otdsTicket));
        return soapWebServiceContext.execute();
    }
}
