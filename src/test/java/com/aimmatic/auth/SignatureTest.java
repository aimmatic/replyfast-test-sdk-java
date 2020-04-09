package com.aimmatic.auth;

import com.squareup.okhttp.*;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.Configuration;
import io.swagger.client.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by veasna on 4/10/18.
 */
public class SignatureTest {

    private static final int noKey = 1;
    private static final int key = 2;
    private static final int keyPair = 3;

    @Test
    public void testSignature() throws IOException, InvalidKeyException {
        String apiKey = "bFCTMlL+UkmoHBWVdDcB6lyXJ/ftOw";
        String secretKey = "dBqsN1L+E0moHBVxbEpRsXhBWGhLTudlEzTfqUggMJGLDzUpSeKomw";
        RuntimeCredentialsProvider.setAimMaticCredentials(new AimMaticCredentials(apiKey, secretKey));
        Signature s = new Signature();
        final Request[] newRequest = {null};
        final int keyType[] = new int[]{noKey};
        final boolean[] hasBody = new boolean[]{false};
        Interceptor.Chain chain = new Interceptor.Chain() {
            @Override
            public Request request() {
                Request.Builder builder = new Request.Builder()
                        .url("https://api.aimmatic.com/v1/sample/one?query1=uui&query2=uua")
                        .addHeader("X-Placenext-Date", "Tue, 10 Nov 2009 23:00:00 UTC")
                        .addHeader("Date", "Tue, 10 Nov 2009 23:00:00 UTC");
                switch (keyType[0]) {
                    case key:
                        builder.addHeader("X-Key", "aimmatic");
                        break;
                    case keyPair:
                        builder.addHeader("X-Key-Pair", "aimmatic");
                        break;
                }
                if (hasBody[0]) {
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), "{\"hello\": 123}");
                    builder.post(body);
                    MediaType releaseConnection = body.contentType();
                    if (releaseConnection != null) {
                        builder.header("Content-Type", releaseConnection.toString());
                    }

                    long response = 0;
                    try {
                        response = body.contentLength();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if (response != -1L) {
                        builder.header("Content-Length", Long.toString(response));
                        builder.removeHeader("Transfer-Encoding");
                    } else {
                        builder.header("Transfer-Encoding", "chunked");
                        builder.removeHeader("Content-Length");
                    }
                }
                return builder.build();
            }

            @Override
            public Response proceed(Request request) throws IOException {
                newRequest[0] = request;
                return null;
            }

            @Override
            public Connection connection() {
                return null;
            }
        };
        // test no api key or secret required
        s.intercept(chain);
        if (newRequest[0] == null) {
            Assert.fail("unable to process new request");
        }
        String auth = newRequest[0].header("Authorization");
        String bodyMd5 = newRequest[0].header("Content-MD5");
        if (auth != null) {
            Assert.fail("signature should not generated on public API");
        }
        if (bodyMd5 != null) {
            Assert.fail("body md5 should not generated on public API");
        }
        // test apikey no body
        keyType[0] = key;
        s.intercept(chain);
        if (newRequest[0] == null) {
            Assert.fail("unable to process new request");
        }
        auth = newRequest[0].header("Authorization");
        bodyMd5 = newRequest[0].header("Content-MD5");
        if (!auth.equals("AimMatic " + apiKey)) {
            Assert.fail("failed to add API Key");
        }
        if (bodyMd5 != null) {
            Assert.fail("body md5 should not generated on API access by API Key");
        }
        // test apikey with body
        keyType[0] = key;
        hasBody[0] = true;
        s.intercept(chain);
        if (newRequest[0] == null) {
            Assert.fail("unable to process new request");
        }
        auth = newRequest[0].header("Authorization");
        bodyMd5 = newRequest[0].header("Content-MD5");
        if (!auth.equals("AimMatic " + apiKey)) {
            Assert.fail("failed to add API Key");
        }
        if (bodyMd5 != null) {
            Assert.fail("body md5 should not generated on API access by API Key");
        }
        // test key pair no body
        keyType[0] = keyPair;
        hasBody[0] = false;
        s.intercept(chain);
        if (newRequest[0] == null) {
            Assert.fail("unable to process new request");
        }
        auth = newRequest[0].header("Authorization");
        bodyMd5 = newRequest[0].header("Content-MD5");
        if (!auth.startsWith("AimMatic " + apiKey + ":")) {
            Assert.fail("failed to add API Key");
        }
        String expectSignature = "nABG0etsKF5DnmC77znEWnBpC6uaKMx+4n+G5+iuljs";
        if (!auth.equals("AimMatic " + apiKey + ":" + expectSignature)) {
            Assert.fail("failed to generate signature expect " + expectSignature + " got " + auth.replace("AimMatic " + apiKey + ":", ""));
        }
        if (bodyMd5 != null) {
            Assert.fail("body md5 should not generated on get method");
        }
        // test key pair with body
        keyType[0] = keyPair;
        hasBody[0] = true;
        s.intercept(chain);
        if (newRequest[0] == null) {
            Assert.fail("unable to process new request");
        }
        auth = newRequest[0].header("Authorization");
        bodyMd5 = newRequest[0].header("Content-MD5");
        if (!auth.startsWith("AimMatic " + apiKey + ":")) {
            Assert.fail("failed to add API Key");
        }
        String expectBodyMd5 = "YewzbgGd1bXoz8MHe3/Ipw";
        if (!bodyMd5.equals(expectBodyMd5)) {
            Assert.fail("body md5 mismatch expect " + expectBodyMd5 + " got " + bodyMd5);
        }
        expectSignature = "+LHaiXN36IdY6wgGQAJSNC2uXFcjEhK2bQUv4ScLsP4";
        if (!auth.equals("AimMatic " + apiKey + ":" + expectSignature)) {
            Assert.fail("failed to generate signature expect " + expectSignature + " got " + auth.replace("AimMatic " + apiKey + ":", ""));
        }
    }

    @Test
    public void testCompleteRequestSignature() throws ApiException, IOException, InvalidKeyException {
        String apiKey = "bFCTMlL+UkmoHBWVdDcB6lyXJ/ftOw";
        String secretKey = "dBqsN1L+E0moHBVxbEpRsXhBWGhLTudlEzTfqUggMJGLDzUpSeKomw";
        RuntimeCredentialsProvider.setAimMaticCredentials(new AimMaticCredentials(apiKey, secretKey));
        final Request[] newRequest = {null};
        ApiClient defaultApiClient = Configuration.getDefaultApiClient();
        defaultApiClient.getHttpClient().networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                newRequest[0] = request;
                return chain.proceed(request);
            }
        });
        ArrayList<Pair> list = new ArrayList<Pair>();
        list.add(new Pair("query1", "uui"));
        list.add(new Pair("query2", "uua"));
        // get request no api key
        Call call = constructRequest("/sample/one", list, null, new String[]{}, defaultApiClient);
        call.execute();
        if (newRequest[0] == null) {
            Assert.fail("request is not intercepted");
        }
        String auth = newRequest[0].header("Authorization");
        String bodyMd5 = newRequest[0].header("Content-MD5");
        if (auth != null) {
            Assert.fail("authorization should be null");
        }
        if (bodyMd5 != null) {
            Assert.fail("body md5 should be null");
        }
        // get request with Api Key
        call = constructRequest("/sample/one", list, null, new String[]{"key"}, defaultApiClient);
        call.execute();
        if (newRequest[0] == null) {
            Assert.fail("request is not intercepted");
        }
        auth = newRequest[0].header("Authorization");
        bodyMd5 = newRequest[0].header("Content-MD5");
        if (auth == null) {
            Assert.fail("failed to add api key");
        }
        if (bodyMd5 != null) {
            Assert.fail("body md5 should be null");
        }
        // post request with api key
        call = constructRequest("/sample/one", list, "{\"hello\": 123}".getBytes(), new String[]{"key"}, defaultApiClient);
        call.execute();
        if (newRequest[0] == null) {
            Assert.fail("request is not intercepted");
        }
        auth = newRequest[0].header("Authorization");
        bodyMd5 = newRequest[0].header("Content-MD5");
        if (auth == null) {
            Assert.fail("failed to add api key");
        }
        if (bodyMd5 != null) {
            Assert.fail("body md5 should be null");
        }
        // post request with key pair
        call = constructRequest("/sample/one", list, "{\"hello\": 123}".getBytes(), new String[]{"keyPair"}, defaultApiClient);
        call.execute();
        if (newRequest[0] == null) {
            Assert.fail("request is not intercepted");
        }
        auth = newRequest[0].header("Authorization");
        bodyMd5 = newRequest[0].header("Content-MD5");
        if (auth == null) {
            Assert.fail("failed to add api key");
        }
        if (bodyMd5 == null) {
            Assert.fail("failed to add body md5");
        }
    }

    private Call constructRequest(String path,
                                  List<Pair> queryParams,
                                  Object body,
                                  String[] auth,
                                  ApiClient apiClient) throws ApiException {
        final String[] localVarContentTypes = {"application/json"};
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        HashMap<String, String> localVarHeaderParams = new HashMap<String, String>();
        localVarHeaderParams.put("Content-Type", localVarContentType);
        return apiClient.buildCall(path, body == null ? "GET" : "POST", queryParams, body, localVarHeaderParams, new HashMap<String, Object>(), auth, null);
    }

}
