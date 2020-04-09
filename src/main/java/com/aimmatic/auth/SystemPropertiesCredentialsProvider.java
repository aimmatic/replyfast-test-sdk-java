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

import static com.aimmatic.Configuration.API_KEY_SYSTEM_PROPERTY;
import static com.aimmatic.Configuration.SECRET_KEY_SYSTEM_PROPERTY;

/**
 * Created by veasna on 4/10/18.
 */
public class SystemPropertiesCredentialsProvider implements AimMaticCredentialsProvider {

    @Override
    public AimMaticCredentials getCredentials() throws InvalidKeyException, CredentialsNotFoundException {
        String apiKey = System.getProperty(API_KEY_SYSTEM_PROPERTY);
        String secretKey = System.getProperty(SECRET_KEY_SYSTEM_PROPERTY);
        if (apiKey == null || apiKey.isEmpty()) {
            throw new InvalidKeyException("api key or secret key invalid");
        }
        if (secretKey != null) {
            secretKey = secretKey.trim();
        }
        return new AimMaticCredentials(apiKey.trim(), secretKey);
    }

}
