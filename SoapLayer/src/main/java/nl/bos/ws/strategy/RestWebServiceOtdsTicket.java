package nl.bos.ws.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import nl.bos.models.OtdsResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class RestWebServiceOtdsTicket implements SoapWebServiceStrategy {
    private final String url;

    private Configuration config = ConfigurationImpl.INSTANCE;

    public RestWebServiceOtdsTicket(String url) {
        this.url = url;
    }

    @Override
    public String run() {
        String ticket = "";
        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        Map<Object, Object> data = new HashMap<>();
        data.put("userName", config.getProperties().getProperty("username"));
        data.put("password", config.getProperties().getProperty("password"));
        data.put("targetResourceId", config.getProperties().getProperty("targetResourceId"));

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(data)))
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .build();


            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonBody = response.body();
            OtdsResponse otdsResponse = objectMapper.readValue(jsonBody, OtdsResponse.class);
            ticket = otdsResponse.ticket();

            // print status code
            System.out.println(response.statusCode());

            // print response body
            System.out.println(response.body());
            System.out.println(ticket);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ticket;
    }
}
