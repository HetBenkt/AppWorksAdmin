package nl.bos;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bos.models.OTDS;

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
        data.put("userName", "awdev");
        data.put("password", "admin");
        data.put("targetResourceId", "7a3eb4ce-b1ec-4acb-bb5c-45e79df2830e");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(data)))
                    .uri(URI.create(url))
                    //.setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                    .header("Content-Type", "application/json")
                    .build();


            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonBody = response.body();
            OTDS otds = objectMapper.readValue(jsonBody, OTDS.class);
            ticket = otds.ticket();

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
