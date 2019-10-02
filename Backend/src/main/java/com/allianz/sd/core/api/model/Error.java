package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Error
 */
/**
 * Error
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:43:54.500Z")

public class Error   {
  @JsonProperty("code")
  private String code = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("severityLevel")
  private String severityLevel = null;

  public Error code(String code) {
    this.code = code;
    return this;
  }

  /**
   * The error code associated with the invalid request.
   * @return code
   **/
  @ApiModelProperty(required = true, value = "The error code associated with the invalid request.")
  @NotNull


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Error message(String message) {
    this.message = message;
    return this;
  }

  /**
   * The error message associated with the invalid request
   * @return message
   **/
  @ApiModelProperty(required = true, value = "The error message associated with the invalid request")
  @NotNull


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Error severityLevel(String severityLevel) {
    this.severityLevel = severityLevel;
    return this;
  }

  /**
   * SeverityLevel Code (\"ERROR\", \"WARNING\", \"INFO\")
   * @return severityLevel
   **/
  @ApiModelProperty(example = "ERROR", required = true, value = "SeverityLevel Code (\"ERROR\", \"WARNING\", \"INFO\")")
  @NotNull


  public String getSeverityLevel() {
    return severityLevel;
  }

  public void setSeverityLevel(String severityLevel) {
    this.severityLevel = severityLevel;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Error error = (Error) o;
    return Objects.equals(this.code, error.code) &&
            Objects.equals(this.message, error.message) &&
            Objects.equals(this.severityLevel, error.severityLevel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, severityLevel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Error {\n");

    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    severityLevel: ").append(toIndentedString(severityLevel)).append("\n");
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
