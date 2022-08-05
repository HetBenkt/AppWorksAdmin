package nl.bos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppWorksPlatformServiceImpl implements AppWorksPlatformService {
    private final String healthUrl;

    public AppWorksPlatformServiceImpl(final String healthUrl) {
        this.healthUrl = healthUrl;
    }

    @Override
    public boolean ping() {
        boolean result;
        try {
            URL url = new URL(healthUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(1_000);
            result = httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK;
            httpURLConnection.disconnect();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
}
