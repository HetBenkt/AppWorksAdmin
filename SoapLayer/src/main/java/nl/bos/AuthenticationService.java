package nl.bos;

public interface AuthenticationService {
    String getToken();

    String getOTDSTicket();

    String getToken(String otdsTicket);
}
