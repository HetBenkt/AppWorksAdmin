package nl.bos;

public interface AuthenticationService {
    String getToken();

    String getOTDSToken();

    String callRestWebServices(String url);
}
