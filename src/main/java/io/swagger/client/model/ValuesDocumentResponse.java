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


package io.swagger.client.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.client.model.BaseResponse;
import java.io.IOException;

/**
 * ValuesDocumentResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-02-28T17:25:36.876-07:00")
public class ValuesDocumentResponse {
  @SerializedName("code")
  private Integer code = null;

  @SerializedName("message")
  private String message = null;

  @SerializedName("values")
  private Object values = null;

  public ValuesDocumentResponse code(Integer code) {
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
  @ApiModelProperty(value = "")
  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public ValuesDocumentResponse message(String message) {
    this.message = message;
    return this;
  }

   /**
   * Get message
   * @return message
  **/
  @ApiModelProperty(value = "")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ValuesDocumentResponse values(Object values) {
    this.values = values;
    return this;
  }

   /**
   * The json data object that was uploaded and stored
   * @return values
  **/
  @ApiModelProperty(value = "The json data object that was uploaded and stored")
  public Object getValues() {
    return values;
  }

  public void setValues(Object values) {
    this.values = values;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ValuesDocumentResponse valuesDocumentResponse = (ValuesDocumentResponse) o;
    return Objects.equals(this.code, valuesDocumentResponse.code) &&
        Objects.equals(this.message, valuesDocumentResponse.message) &&
        Objects.equals(this.values, valuesDocumentResponse.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, values);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ValuesDocumentResponse {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

