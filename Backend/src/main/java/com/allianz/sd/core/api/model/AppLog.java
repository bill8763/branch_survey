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
 * AppLog
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class AppLog   {
  @JsonProperty("FunctionID")
  private String functionID = null;

  @JsonProperty("Action")
  private String action = null;

  @JsonProperty("Time")
  private String time = null;

  @JsonProperty("Valid")
  private String valid = null;

  @JsonProperty("Message")
  private String message = null;

  @JsonProperty("DeviceSystem")
  private String deviceSystem = null;

  @JsonProperty("DeviceModel")
  private String deviceModel = null;

  public AppLog functionID(String functionID) {
    this.functionID = functionID;
    return this;
  }

  /**
   * app function name
   * @return functionID
  **/
  @ApiModelProperty(example = "Customer | Calendar", value = "app function name")


  public String getFunctionID() {
    return functionID;
  }

  public void setFunctionID(String functionID) {
    this.functionID = functionID;
  }

  public AppLog action(String action) {
    this.action = action;
    return this;
  }

  /**
   * app action name
   * @return action
  **/
  @ApiModelProperty(example = "Add / Edit / Delete", value = "app action name")


  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public AppLog time(String time) {
    this.time = time;
    return this;
  }

  /**
   * log time
   * @return time
  **/
  @ApiModelProperty(example = "2019-02-27T00:10:00+0800", value = "log time")


  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public AppLog valid(String valid) {
    this.valid = valid;
    return this;
  }

  /**
   * action isValid or not
   * @return valid
  **/
  @ApiModelProperty(example = "Y | N", value = "action isValid or not")


  public String getValid() {
    return valid;
  }

  public void setValid(String valid) {
    this.valid = valid;
  }

  public AppLog message(String message) {
    this.message = message;
    return this;
  }

  /**
   * if not Valid show the message
   * @return message
  **/
  @ApiModelProperty(example = "app click not valid", value = "if not Valid show the message")


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public AppLog deviceSystem(String deviceSystem) {
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

  public AppLog deviceModel(String deviceModel) {
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
    AppLog appLog = (AppLog) o;
    return Objects.equals(this.functionID, appLog.functionID) &&
        Objects.equals(this.action, appLog.action) &&
        Objects.equals(this.time, appLog.time) &&
        Objects.equals(this.valid, appLog.valid) &&
        Objects.equals(this.message, appLog.message) &&
        Objects.equals(this.deviceSystem, appLog.deviceSystem) &&
        Objects.equals(this.deviceModel, appLog.deviceModel);
  }

  @Override
  public int hashCode() {
    return Objects.hash(functionID, action, time, valid, message, deviceSystem, deviceModel);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppLog {\n");
    
    sb.append("    functionID: ").append(toIndentedString(functionID)).append("\n");
    sb.append("    action: ").append(toIndentedString(action)).append("\n");
    sb.append("    time: ").append(toIndentedString(time)).append("\n");
    sb.append("    valid: ").append(toIndentedString(valid)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
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

