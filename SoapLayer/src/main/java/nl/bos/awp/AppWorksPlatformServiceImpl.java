package nl.bos.awp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class AppWorksPlatformServiceImpl implements AppWorksPlatformService {
    private final String healthUrl;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public AppWorksPlatformServiceImpl(final String healthUrl) {
        this.healthUrl = healthUrl;
    }

    @Override
    public boolean ping() {
        boolean result;
        int timeout = 1_000;

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout).build();

        try (CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            final HttpGet httpGet = new HttpGet(healthUrl);
            httpGet.setHeader("Content-type", ContentType.APPLICATION_JSON.getMimeType());
            CloseableHttpResponse response = client.execute(httpGet);
            String responseJsonBody = formatJson(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
            logger.info(responseJsonBody);
            result = true;
        } catch (IOException e) {
            result = false;
        }
        return result;
    }

    private String formatJson(String uglyJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(uglyJsonString);
        return gson.toJson(je);
    }
}
