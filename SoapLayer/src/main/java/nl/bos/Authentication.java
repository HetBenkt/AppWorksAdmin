package nl.bos;

public interface Authentication {
    String getToken();

    String getOTDSTicket();

    String getToken(String otdsTicket);
}
