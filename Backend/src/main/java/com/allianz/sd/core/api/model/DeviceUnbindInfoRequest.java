package com.allianz.sd.core.api.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * DeviceUnbindInfoRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-06T03:16:57.779Z")

public class DeviceUnbindInfoRequest   {
  @JsonProperty("DeviceId")
  private String deviceId = null;

  public DeviceUnbindInfoRequest deviceId(String deviceId) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeviceUnbindInfoRequest deviceUnbindInfoRequest = (DeviceUnbindInfoRequest) o;
    return Objects.equals(this.deviceId, deviceUnbindInfoRequest.deviceId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeviceUnbindInfoRequest {\n");
    
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
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

