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
 * LoginRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class LoginRequest   {
  @JsonProperty("Username")
  private String username = null;

  @JsonProperty("Password")
  private String password = null;

  @JsonProperty("DeviceId")
  private String deviceId = null;

  @JsonProperty("DeviceSystem")
  private String deviceSystem = null;

  @JsonProperty("DeviceModel")
  private String deviceModel = null;

  @JsonProperty("DeviceType")
  private String deviceType = null;

  @JsonProperty("PushId")
  private String pushId = null;

  public LoginRequest username(String username) {
    this.username = username;
    return this;
  }

  /**
   * username of agent
   * @return username
  **/
  @ApiModelProperty(example = "897726", required = true, value = "username of agent")
  @NotNull


  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public LoginRequest password(String password) {
    this.password = password;
    return this;
  }

  /**
   * password of agent
   * @return password
  **/
  @ApiModelProperty(example = "1234567", required = true, value = "password of agent")
  @NotNull


  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LoginRequest deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }

  /**
   * deviceID of agent use phone or tablet
   * @return deviceId
  **/
  @ApiModelProperty(example = "7a0ef65159b5899", required = true, value = "deviceID of agent use phone or tablet")
  @NotNull


  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public LoginRequest deviceSystem(String deviceSystem) {
    this.deviceSystem = deviceSystem;
    return this;
  }

  /**
   * device of system
   * @return deviceSystem
  **/
  @ApiModelProperty(example = "IOS / Android / Windows", required = true, value = "device of system")
  @NotNull


  public String getDeviceSystem() {
    return deviceSystem;
  }

  public void setDeviceSystem(String deviceSystem) {
    this.deviceSystem = deviceSystem;
  }

  public LoginRequest deviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
    return this;
  }

  /**
   * device of model
   * @return deviceModel
  **/
  @ApiModelProperty(example = "HTC_D816x、iPhone9,3", required = true, value = "device of model")
  @NotNull


  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public LoginRequest deviceType(String deviceType) {
    this.deviceType = deviceType;
    return this;
  }

  /**
   * device type is pad or phone
   * @return deviceType
  **/
  @ApiModelProperty(example = "Pad / Phone", required = true, value = "device type is pad or phone")
  @NotNull


  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public LoginRequest pushId(String pushId) {
    this.pushId = pushId;
    return this;
  }

  /**
   * firebase push id of device
   * @return pushId
  **/
  @ApiModelProperty(example = "dD866fOP6ws:APA91bFzBn-wx6BgRN5HyaNMrok0HBw1pMypGFVdOgMq13O6zCJ9yWmeduC_Xg8PcBrBu3ZttwXUuxiAY0X4bHCnVti6cdJK3ka4FLrKMPpIK9swYtcP_GVh7M5mwD17F29qCacRMWuc", required = true, value = "firebase push id of device")
  @NotNull


  public String getPushId() {
    return pushId;
  }

  public void setPushId(String pushId) {
    this.pushId = pushId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginRequest loginRequest = (LoginRequest) o;
    return Objects.equals(this.username, loginRequest.username) &&
        Objects.equals(this.password, loginRequest.password) &&
        Objects.equals(this.deviceId, loginRequest.deviceId) &&
        Objects.equals(this.deviceSystem, loginRequest.deviceSystem) &&
        Objects.equals(this.deviceModel, loginRequest.deviceModel) &&
        Objects.equals(this.deviceType, loginRequest.deviceType) &&
        Objects.equals(this.pushId, loginRequest.pushId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, password, deviceId, deviceSystem, deviceModel, deviceType, pushId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginRequest {\n");
    
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    deviceSystem: ").append(toIndentedString(deviceSystem)).append("\n");
    sb.append("    deviceModel: ").append(toIndentedString(deviceModel)).append("\n");
    sb.append("    deviceType: ").append(toIndentedString(deviceType)).append("\n");
    sb.append("    pushId: ").append(toIndentedString(pushId)).append("\n");
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

