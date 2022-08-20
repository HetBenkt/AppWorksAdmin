package nl.bos.ws.strategy;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.bos.config.Configuration;
import nl.bos.config.ConfigurationImpl;
import nl.bos.exception.GeneralAppException;
import nl.bos.models.OtdsResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RestWebServiceOtdsTicket implements SoapWebServiceStrategy {
    private final String url;

    private static final Configuration config = ConfigurationImpl.INSTANCE;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public RestWebServiceOtdsTicket(final String url) {
        this.url = url;
    }

    @Override
    public String run() {
        String ticket;

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        try {
            String json = "{\"userName\":\"" + config.getProperties().getProperty("username") + "\",\"password\":\"" + config.getProperties().getProperty("password") + "\",\"targetResourceId\":\"" + config.getProperties().getProperty("targetResourceId") + "\"}";
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            String responseJsonBody = EntityUtils.toString(response.getEntity(), "UTF-8");

            OtdsResponse otdsResponse = new ObjectMapper().readValue(responseJsonBody, OtdsResponse.class);
            ticket = otdsResponse.ticket();

            if (logger.getLevel() == Level.INFO) {
                String msg = MessageFormat.format("Status: {0}; Body: {1}; Ticket: {2}", response.getStatusLine().getStatusCode(), responseJsonBody, ticket);
                logger.info(msg);
            }

            client.close();
        } catch (IOException e) {
            throw new GeneralAppException(e);
        }

        return ticket;
    }
}
