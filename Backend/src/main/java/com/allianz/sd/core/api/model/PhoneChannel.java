package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PhoneChannel
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class PhoneChannel   {
  @JsonProperty("phoneID")
  private BigDecimal phoneID = null;

  @JsonProperty("usageType")
  private String usageType = null;

  @JsonProperty("phoneNumber")
  private String phoneNumber = null;

  @JsonProperty("isChangeable")
  private Boolean isChangeable = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public PhoneChannel phoneID(BigDecimal phoneID) {
    this.phoneID = phoneID;
    return this;
  }

  /**
   * id of tel. Can be null. When null new Phone will be created on the backend.
   * @return phoneID
  **/
  @ApiModelProperty(example = "100.0", value = "id of tel. Can be null. When null new Phone will be created on the backend.")

  @Valid

  public BigDecimal getPhoneID() {
    return phoneID;
  }

  public void setPhoneID(BigDecimal phoneID) {
    this.phoneID = phoneID;
  }

  public PhoneChannel usageType(String usageType) {
    this.usageType = usageType;
    return this;
  }

  /**
   * type of telefone connections e.g. MOBILE, HOME, WORK. From dataList
   * @return usageType
  **/
  @ApiModelProperty(example = "MOBILE", value = "type of telefone connections e.g. MOBILE, HOME, WORK. From dataList")


  public String getUsageType() {
    return usageType;
  }

  public void setUsageType(String usageType) {
    this.usageType = usageType;
  }

  public PhoneChannel phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * number of Tel
   * @return phoneNumber
  **/
  @ApiModelProperty(example = "0987654321", value = "number of Tel")


  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public PhoneChannel isChangeable(Boolean isChangeable) {
    this.isChangeable = isChangeable;
    return this;
  }

  /**
   * When customer is from OPUS or other core systems main specific attribute cant be chagensdatasource. true can be changed.
   * @return isChangeable
  **/
  @ApiModelProperty(example = "false", required = true, value = "When customer is from OPUS or other core systems main specific attribute cant be chagensdatasource. true can be changed.")
  @NotNull


  public Boolean isIsChangeable() {
    return isChangeable;
  }

  public void setIsChangeable(Boolean isChangeable) {
    this.isChangeable = isChangeable;
  }

  public PhoneChannel extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public PhoneChannel addExtensionsItem(Extension extensionsItem) {
    if (this.extensions == null) {
      this.extensions = new ArrayList<Extension>();
    }
    this.extensions.add(extensionsItem);
    return this;
  }

  /**
   * extension attributes
   * @return extensions
  **/
  @ApiModelProperty(value = "extension attributes")

  @Valid

  public List<Extension> getExtensions() {
    return extensions;
  }

  public void setExtensions(List<Extension> extensions) {
    this.extensions = extensions;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PhoneChannel phoneChannel = (PhoneChannel) o;
    return Objects.equals(this.phoneID, phoneChannel.phoneID) &&
        Objects.equals(this.usageType, phoneChannel.usageType) &&
        Objects.equals(this.phoneNumber, phoneChannel.phoneNumber) &&
        Objects.equals(this.isChangeable, phoneChannel.isChangeable) &&
        Objects.equals(this.extensions, phoneChannel.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(phoneID, usageType, phoneNumber, isChangeable, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PhoneChannel {\n");
    
    sb.append("    phoneID: ").append(toIndentedString(phoneID)).append("\n");
    sb.append("    usageType: ").append(toIndentedString(usageType)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    isChangeable: ").append(toIndentedString(isChangeable)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
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

