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

package com.aimmatic;

/**
 * Created by veasna on 4/10/18.
 */
public class Configuration {

    /**
     * System property name for the AimMatic api key
     */
    public static final String API_KEY_SYSTEM_PROPERTY = "aimmatic.apiKey";

    /**
     * System property name for the AimMatic secret key
     */
    public static final String SECRET_KEY_SYSTEM_PROPERTY = "aimmatic.secretKey";

    /**
     * Environment variable name for the AimMatic api key
     */
    public static final String API_KEY_ENV_VAR = "AIMMATIC_API_KEY";

    /**
     * Environment variable name for the AimMatic secret key
     */
    public static final String SECRET_KEY_ENV_VAR = "AIMMATIC_SECRET_KEY";

    /**
     * A user-agent of AimMatic SDK
     */
    public static final String USER_AGENT = "aimmatic 1.0";

}
