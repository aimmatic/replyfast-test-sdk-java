package com.aimmatic.auth;

import com.aimmatic.Configuration;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.EnvironmentVariables;

import java.util.Base64;

/**
 * Created by veasna on 4/10/18.
 */

public class CredentialsProviderTest {

    @Rule
    public final EnvironmentVariables environmentVariables = new EnvironmentVariables();

    @Test
    public void testCredentialsProvider() throws InvalidKeyException {
        String apiKey = "bFCTMlL+UkmoHBWVdDcB6lyXJ/ftOw";
        String secretKey = "dBqsN1L+E0moHBVxbEpRsXhBWGhLTudlEzTfqUggMJGLDzUpSeKomw";
        byte[] secretKeyInByte = Base64.getDecoder().decode(secretKey);
        byte[] expectedByte = {116, 26, -84, 55, 82, -2, 19, 73, -88, 28, 21, 113, 108, 74, 81, -79, 120, 65, 88, 104, 75, 78, -25, 101, 19, 52, -33, -87, 72, 32, 48, -111, -117, 15, 53, 41, 73, -30, -88, -101};
        // test system properties
        System.setProperty(Configuration.API_KEY_SYSTEM_PROPERTY, apiKey);
        System.setProperty(Configuration.SECRET_KEY_SYSTEM_PROPERTY, secretKey);
        SystemPropertiesCredentialsProvider spcp = new SystemPropertiesCredentialsProvider();
        Assert.assertEquals(apiKey, spcp.getCredentials().getApiKey());
        Assert.assertArrayEquals(secretKeyInByte, spcp.getCredentials().getSecretKey());
        Assert.assertArrayEquals(expectedByte, spcp.getCredentials().getSecretKey());
        // test environment variable
        environmentVariables.set(Configuration.API_KEY_ENV_VAR, apiKey);
        environmentVariables.set(Configuration.SECRET_KEY_ENV_VAR, secretKey);
        EnvironmentVariableCredentialsProvider evcp = new EnvironmentVariableCredentialsProvider();
        Assert.assertEquals(apiKey, evcp.getCredentials().getApiKey());
        Assert.assertArrayEquals(secretKeyInByte, evcp.getCredentials().getSecretKey());
        Assert.assertArrayEquals(expectedByte, evcp.getCredentials().getSecretKey());
    }

}
