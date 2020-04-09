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

import static com.aimmatic.Configuration.API_KEY_ENV_VAR;
import static com.aimmatic.Configuration.SECRET_KEY_ENV_VAR;


/**
 * Created by veasna on 4/10/18.
 */
public class EnvironmentVariableCredentialsProvider implements AimMaticCredentialsProvider {

    @Override
    public AimMaticCredentials getCredentials() throws InvalidKeyException, CredentialsNotFoundException {
        String apiKey = System.getenv(API_KEY_ENV_VAR);
        if (apiKey == null) {
            throw new CredentialsNotFoundException("Api Key not found");
        }
        apiKey = apiKey.trim();
        if (apiKey.isEmpty()) {
            throw new InvalidKeyException("Api Key or Secret Key is empty");
        }

        String secretKey = System.getenv(SECRET_KEY_ENV_VAR);
        if (secretKey != null) {
            secretKey = secretKey.trim();
        }
        return new AimMaticCredentials(apiKey, secretKey);
    }

}
