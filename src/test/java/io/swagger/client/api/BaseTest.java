package io.swagger.client.api;

import com.aimmatic.auth.AimMaticCredentials;
import com.aimmatic.auth.InvalidKeyException;
import com.aimmatic.auth.RuntimeCredentialsProvider;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public String getAudioId() {
        return "L8Ak34fVavwsLxX4l9w";
    }

    public void setup(ApiClient client) throws ApiException {
        String apiKey = "+0gme9LsJNxPoW6ZPwHwXwBW";
        String secretKey = "SNwR0vYd3Ee29xVuRlVnbnRTQTUwZpOl8mvP9kLOAAG8b56uL1cU9Q";
        try {
            RuntimeCredentialsProvider.setAimMaticCredentials(new AimMaticCredentials(apiKey, secretKey));
        } catch (InvalidKeyException e) {
            throw new ApiException(e);
        }
        //String baseApiUrl = "http://localhost:8080/v1";
        String baseApiUrl = "https://api.aimmatic.com/v1";
        client.setDebugging(true);
        client.setBasePath(baseApiUrl);
        client.setConnectTimeout(6000);
        client.getHttpClient().setReadTimeout(5, TimeUnit.MINUTES);
    }


}
