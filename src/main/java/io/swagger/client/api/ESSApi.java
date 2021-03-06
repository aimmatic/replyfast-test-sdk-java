/*
 * Natural Voice SDK
 * Natural Voice SDKs are easiest and best supported way for most developers to quickly build and iterate Natural Voice applications that integrate with our services programmatically.
 *
 * OpenAPI spec version: 1.2.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package io.swagger.client.api;

import io.swagger.client.ApiCallback;
import io.swagger.client.ApiClient;
import io.swagger.client.ApiException;
import io.swagger.client.ApiResponse;
import io.swagger.client.Configuration;
import io.swagger.client.Pair;
import io.swagger.client.ProgressRequestBody;
import io.swagger.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import io.swagger.client.model.ESSResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ESSApi {
    private ApiClient apiClient;

    public ESSApi() {
        this(Configuration.getDefaultApiClient());
    }

    public ESSApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for insightsEssGet
     * @param saliencevalue (required) Floating point number, 0 is the recommended value, 1 and 2 are also accepted, value indicates the salience factor applied in the calculation of ESS weights. (required)
     * @param start (optional) UTC start time in millisecond, must not include audioId. (optional)
     * @param end (optional) UTC end time in millisecond, must not include audioId. (optional)
     * @param progressListener Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call insightsEssGetCall(Float saliencevalue, Long start, Long end, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;
        
        // create path and map variables
        String localVarPath = "/insights/ess";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        if (saliencevalue != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "saliencevalue", saliencevalue));
        if (start != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "start", start));
        if (end != null)
        localVarQueryParams.addAll(apiClient.parameterToPairs("", "end", end));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if(progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                    .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                    .build();
                }
            });
        }

        String[] localVarAuthNames = new String[] {  };
        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAuthNames, progressRequestListener);
    }
    
    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call insightsEssGetValidateBeforeCall(Float saliencevalue, Long start, Long end, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        
        // verify the required parameter 'saliencevalue' is set
        if (saliencevalue == null) {
            throw new ApiException("Missing the required parameter 'saliencevalue' when calling insightsEssGet(Async)");
        }
        
        
        com.squareup.okhttp.Call call = insightsEssGetCall(saliencevalue, start, end, progressListener, progressRequestListener);
        return call;

        
        
        
        
    }

    /**
     * Calculate the Entity Sentiment Score
     * Use this API to calculate ESS. This is a GET operation. Optionally can provide a start and end date range to retrieve the response for a document corpus that have NLP results.
     * @param saliencevalue (required) Floating point number, 0 is the recommended value, 1 and 2 are also accepted, value indicates the salience factor applied in the calculation of ESS weights. (required)
     * @param start (optional) UTC start time in millisecond, must not include audioId. (optional)
     * @param end (optional) UTC end time in millisecond, must not include audioId. (optional)
     * @return ESSResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ESSResponse insightsEssGet(Float saliencevalue, Long start, Long end) throws ApiException {
        ApiResponse<ESSResponse> resp = insightsEssGetWithHttpInfo(saliencevalue, start, end);
        return resp.getData();
    }

    /**
     * Calculate the Entity Sentiment Score
     * Use this API to calculate ESS. This is a GET operation. Optionally can provide a start and end date range to retrieve the response for a document corpus that have NLP results.
     * @param saliencevalue (required) Floating point number, 0 is the recommended value, 1 and 2 are also accepted, value indicates the salience factor applied in the calculation of ESS weights. (required)
     * @param start (optional) UTC start time in millisecond, must not include audioId. (optional)
     * @param end (optional) UTC end time in millisecond, must not include audioId. (optional)
     * @return ApiResponse&lt;ESSResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<ESSResponse> insightsEssGetWithHttpInfo(Float saliencevalue, Long start, Long end) throws ApiException {
        com.squareup.okhttp.Call call = insightsEssGetValidateBeforeCall(saliencevalue, start, end, null, null);
        Type localVarReturnType = new TypeToken<ESSResponse>(){}.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Calculate the Entity Sentiment Score (asynchronously)
     * Use this API to calculate ESS. This is a GET operation. Optionally can provide a start and end date range to retrieve the response for a document corpus that have NLP results.
     * @param saliencevalue (required) Floating point number, 0 is the recommended value, 1 and 2 are also accepted, value indicates the salience factor applied in the calculation of ESS weights. (required)
     * @param start (optional) UTC start time in millisecond, must not include audioId. (optional)
     * @param end (optional) UTC end time in millisecond, must not include audioId. (optional)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call insightsEssGetAsync(Float saliencevalue, Long start, Long end, final ApiCallback<ESSResponse> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = insightsEssGetValidateBeforeCall(saliencevalue, start, end, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<ESSResponse>(){}.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
