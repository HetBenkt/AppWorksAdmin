package nl.bos.auth;

public interface Authentication {
    String getToken();

    String getOTDSTicket();

    String getToken(String otdsTicket);
}
