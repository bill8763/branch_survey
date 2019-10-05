package com.allianz.sd.core.api.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * LoginResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-06T03:16:57.779Z")

public class LoginResponse   {
  @JsonProperty("success")
  private Boolean success = null;

  @JsonProperty("token")
  private String token = null;

  public LoginResponse success(Boolean success) {
    this.success = success;
    return this;
  }

  /**
   * backend update data is success
   * @return success
  **/
  @ApiModelProperty(example = "true", required = true, value = "backend update data is success")
  @NotNull


  public Boolean isSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public LoginResponse token(String token) {
    this.token = token;
    return this;
  }

  /**
   * JWT of login
   * @return token
  **/
  @ApiModelProperty(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJBZ2VudElEIjoxLCJBcHBVc2VNb2RlIjoiQUciLCJBZ2VudE5hbWUiOiJKb2huIiwiR29hbFNpZ25pbmdTdXBlcnZpc29yIjoyfQ==.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", required = true, value = "JWT of login")
  @NotNull


  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginResponse loginResponse = (LoginResponse) o;
    return Objects.equals(this.success, loginResponse.success) &&
        Objects.equals(this.token, loginResponse.token);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, token);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginResponse {\n");
    
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
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

