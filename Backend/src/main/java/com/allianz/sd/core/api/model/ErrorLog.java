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
 * ErrorLog
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class ErrorLog   {
  @JsonProperty("Message")
  private String message = null;

  @JsonProperty("Stack")
  private String stack = null;

  @JsonProperty("Time")
  private String time = null;

  @JsonProperty("DeviceSystem")
  private String deviceSystem = null;

  @JsonProperty("DeviceModel")
  private String deviceModel = null;

  public ErrorLog message(String message) {
    this.message = message;
    return this;
  }

  /**
   * error message
   * @return message
  **/
  @ApiModelProperty(example = "error message", value = "error message")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorLog stack(String stack) {
    this.stack = stack;
    return this;
  }

  /**
   * error stack
   * @return stack
  **/
  @ApiModelProperty(example = "error stack", value = "error stack")


  public String getStack() {
    return stack;
  }

  public void setStack(String stack) {
    this.stack = stack;
  }

  public ErrorLog time(String time) {
    this.time = time;
    return this;
  }

  /**
   * catch error time
   * @return time
  **/
  @ApiModelProperty(example = "2019-02-27T00:10:00+0800", value = "catch error time")


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public ErrorLog deviceSystem(String deviceSystem) {
    this.deviceSystem = deviceSystem;
    return this;
  }

  /**
   * DeviceSystem
   * @return deviceSystem
  **/
  @ApiModelProperty(example = "DeviceSystem", value = "DeviceSystem")


  public String getDeviceSystem() {
    return deviceSystem;
  }

  public void setDeviceSystem(String deviceSystem) {
    this.deviceSystem = deviceSystem;
  }

  public ErrorLog deviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
    return this;
  }

  /**
   * DeviceModel
   * @return deviceModel
  **/
  @ApiModelProperty(example = "DeviceModel", value = "DeviceModel")


  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorLog errorLog = (ErrorLog) o;
    return Objects.equals(this.message, errorLog.message) &&
        Objects.equals(this.stack, errorLog.stack) &&
        Objects.equals(this.time, errorLog.time) &&
        Objects.equals(this.deviceSystem, errorLog.deviceSystem) &&
        Objects.equals(this.deviceModel, errorLog.deviceModel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, stack, time, deviceSystem, deviceModel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorLog {\n");
    
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    stack: ").append(toIndentedString(stack)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    deviceSystem: ").append(toIndentedString(deviceSystem)).append("\n");
    sb.append("    deviceModel: ").append(toIndentedString(deviceModel)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

