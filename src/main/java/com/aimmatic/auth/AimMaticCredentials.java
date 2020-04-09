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

import java.util.Base64;

/**
 * Created by veasna on 4/10/18.
 */
public class AimMaticCredentials {

    // a binary represent secret key
    private byte[] secretKey;

    // a string represent an api key
    private String apiKey;

    public AimMaticCredentials(String apiKey, String secretKey) throws InvalidKeyException {
        if (apiKey.isEmpty()) {
            throw new InvalidKeyException("API Key is empty");
        }
        this.apiKey = apiKey;
        if (secretKey != null && !secretKey.isEmpty()) {
            try {
                this.secretKey = Base64.getDecoder().decode(secretKey);
            } catch (Exception e) {
                throw new InvalidKeyException(e);
            }
        }
    }

    public byte[] getSecretKey() {
        if (secretKey != null) {
            return secretKey;
        }
        throw new CredentialsNotFoundException("Secret key not found");
    }

    public String getApiKey() {
        return apiKey;
    }
}
