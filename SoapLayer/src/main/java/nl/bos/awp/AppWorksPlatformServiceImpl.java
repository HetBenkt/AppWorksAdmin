package nl.bos.awp;

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
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(healthUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(1_000);
            result = httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            result = false;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return result;
    }
}
