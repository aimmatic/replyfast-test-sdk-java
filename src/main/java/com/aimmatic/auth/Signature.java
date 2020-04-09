/*
Copyright 2018 The AimMatic Authors.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
    http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.aimmatic.auth;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import okio.Buffer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

/**
 * Created by veasna on 4/10/18.
 */
public class Signature implements Interceptor {

    private static final String contentMd5 = "Content-MD5";
    private static final String contentType = "Content-Type";
    private static final String contentLength = "Content-Length";
    private static final String authorization = "Authorization";
    private static final String date = "Date";
    private static final String aimmaticDate = "X-Placenext-Date";

    private static final String key = "X-Key";
    private static final String keyPair = "X-Key-Pair";

    private AimMaticCredentialsProvider[] credentialsProviders;
    private AimMaticCredentialsProvider lastUsedCredentialsProvider;

    private static Object lock = new Object();

    /**
     *
     */
    public Signature() {
        credentialsProviders = new AimMaticCredentialsProvider[]{
                new RuntimeCredentialsProvider(),
                new EnvironmentVariableCredentialsProvider(),
                new SystemPropertiesCredentialsProvider()
        };
    }

    /**
     * reset last used of credentials provider
     */
    public void resetLastUsedCredentialsProvider() {
        synchronized (lock) {
            lastUsedCredentialsProvider = null;
        }
    }

    /*
     * Get credentials from the last use credentials provider
     */
    private AimMaticCredentials getCredentials() throws CredentialsNotFoundException, InvalidKeyException {
        if (lastUsedCredentialsProvider == null) {
            synchronized (lock) {
                for (AimMaticCredentialsProvider provider : credentialsProviders) {
                    try {
                        if (provider.getCredentials() != null) {
                            lastUsedCredentialsProvider = provider;
                        }
                    } catch (Exception e) {
                    }
                }
            }
            if (lastUsedCredentialsProvider == null) {
                throw new CredentialsNotFoundException("no credential found");
            }
        }
        return lastUsedCredentialsProvider.getCredentials();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.header(key) != null) {
            // api key alone
            try {
                request = request.newBuilder()
                        .addHeader(authorization, "AimMatic " + getCredentials().getApiKey())
                        .removeHeader(key)
                        .build();
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        } else if (request.header(keyPair) != null) {
            // api key + secret key
            if (request.header(aimmaticDate) == null) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyyy HH:mm:ss z");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                String dateTime = sdf.format(new Date());
                request = request.newBuilder().addHeader(aimmaticDate, dateTime).addHeader(date, dateTime).build();
            }
            try {
                String bodyMd5 = calculateBodyMd5(request);
                String signature = calculateSignature(request, bodyMd5);
                Request.Builder builder = request.newBuilder();
                if (bodyMd5.length() > 0) {
                    builder.addHeader(contentMd5, bodyMd5);
                }
                request = builder.addHeader(authorization, "AimMatic " + getCredentials().getApiKey() + ":" + signature)
                        .removeHeader(keyPair)
                        .build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return chain.proceed(request);
    }

    private String calculateBodyMd5(Request request) throws NoSuchAlgorithmException, IOException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        if (request.header(contentLength) != null) {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return Base64.getEncoder().withoutPadding().encodeToString(md5.digest(buffer.readByteArray()));
        } else {
            return "";
        }
    }

    private String calculateSignature(Request request, String bodyMd5)
            throws MissingContentTypeException, InvalidKeyException, NoSuchAlgorithmException, java.security.InvalidKeyException {
        StringBuilder sb = new StringBuilder();
        // add md5 body
        if (bodyMd5.length() > 0) {
            sb.append(bodyMd5);
            sb.append('\n');
        }
        // add content type if a available
        if (request.header(contentLength) != null) {
            if (request.header(contentType) == null) {
                throw new MissingContentTypeException();
            }
            sb.append(request.header(contentType));
            sb.append('\n');
        }
        // add date header
        if (request.header(aimmaticDate) != null) {
            sb.append(request.header(aimmaticDate));
            sb.append('\n');
        } else if (request.header(date) != null) {
            sb.append(request.header(date));
            sb.append('\n');
        }
        // concatenate header
        sb.append(concatenateHeader(request));
        sb.append('\n');
        // write Url
        sb.append(request.url().toString());
        SecretKeySpec key = new SecretKeySpec(getCredentials().getSecretKey(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(key);
        byte[] signature = mac.doFinal(sb.toString().getBytes());
        return Base64.getEncoder().withoutPadding().encodeToString(signature);
    }

    private String concatenateHeader(Request request) {
        Headers header = request.headers();
        Set<String> names = header.names();
        // Note: OkHTTP already use TreeSet where is order the header by key
        String concatenate = "";
        // TODO: filter redundant header and concatenate all value together
        for (String key : names) {
            String lkey = key.toLowerCase();
            if (lkey.startsWith("x-placenext")) {
                concatenate += key.toLowerCase() + ":" + header.get(key);
            }
        }
        return concatenate;
    }

}
