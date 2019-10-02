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
 * Address
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class Address   {
  @JsonProperty("addressId")
  private BigDecimal addressId = null;

  @JsonProperty("addressType")
  private String addressType = null;

  @JsonProperty("countryCode")
  private String countryCode = null;

  @JsonProperty("city")
  private String city = null;

  @JsonProperty("area")
  private String area = null;

  @JsonProperty("postCode")
  private String postCode = null;

  @JsonProperty("line1")
  private String line1 = null;

  @JsonProperty("line2")
  private String line2 = null;

  @JsonProperty("line3")
  private String line3 = null;

  @JsonProperty("line4")
  private String line4 = null;

  @JsonProperty("isChangeable")
  private Boolean isChangeable = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public Address addressId(BigDecimal addressId) {
    this.addressId = addressId;
    return this;
  }

  /**
   * Address id of customer. Can be null. When null a new address is created in the backend.
   * @return addressId
  **/
  @ApiModelProperty(example = "123.0", value = "Address id of customer. Can be null. When null a new address is created in the backend.")

  @Valid

  public BigDecimal getAddressId() {
    return addressId;
  }

  public void setAddressId(BigDecimal addressId) {
    this.addressId = addressId;
  }

  public Address addressType(String addressType) {
    this.addressType = addressType;
    return this;
  }

  /**
   * type of address
   * @return addressType
  **/
  @ApiModelProperty(example = "AddressTypeHome", value = "type of address")


  public String getAddressType() {
    return addressType;
  }

  public void setAddressType(String addressType) {
    this.addressType = addressType;
  }

  public Address countryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  /**
   * The Country code, [ISO 3166-2]
   * @return countryCode
  **/
  @ApiModelProperty(example = "TW", value = "The Country code, [ISO 3166-2]")


  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public Address city(String city) {
    this.city = city;
    return this;
  }

  /**
   * The city where the address is located
   * @return city
  **/
  @ApiModelProperty(example = "Taipei", value = "The city where the address is located")


  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Address area(String area) {
    this.area = area;
    return this;
  }

  /**
   * The area where the address is located
   * @return area
  **/
  @ApiModelProperty(example = "Songshan", value = "The area where the address is located")


  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public Address postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

  /**
   * The Postal Code
   * @return postCode
  **/
  @ApiModelProperty(example = "345346", value = "The Postal Code")


  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public Address line1(String line1) {
    this.line1 = line1;
    return this;
  }

  /**
   * The address line 1
   * @return line1
  **/
  @ApiModelProperty(example = "99-21, Marbella", value = "The address line 1")


  public String getLine1() {
    return line1;
  }

  public void setLine1(String line1) {
    this.line1 = line1;
  }

  public Address line2(String line2) {
    this.line2 = line2;
    return this;
  }

  /**
   * The address line 2
   * @return line2
  **/
  @ApiModelProperty(example = "Bugist St", value = "The address line 2")


  public String getLine2() {
    return line2;
  }

  public void setLine2(String line2) {
    this.line2 = line2;
  }

  public Address line3(String line3) {
    this.line3 = line3;
    return this;
  }

  /**
   * The address line 3
   * @return line3
  **/
  @ApiModelProperty(example = "Kow Loon, Ho Main Tin", value = "The address line 3")


  public String getLine3() {
    return line3;
  }

  public void setLine3(String line3) {
    this.line3 = line3;
  }

  public Address line4(String line4) {
    this.line4 = line4;
    return this;
  }

  /**
   * The address line 4
   * @return line4
  **/
  @ApiModelProperty(example = "Kow Loon, Ho Main Tin", value = "The address line 4")


  public String getLine4() {
    return line4;
  }

  public void setLine4(String line4) {
    this.line4 = line4;
  }

  public Address isChangeable(Boolean isChangeable) {
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

  public Address extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Address addExtensionsItem(Extension extensionsItem) {
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
    Address address = (Address) o;
    return Objects.equals(this.addressId, address.addressId) &&
        Objects.equals(this.addressType, address.addressType) &&
        Objects.equals(this.countryCode, address.countryCode) &&
        Objects.equals(this.city, address.city) &&
        Objects.equals(this.area, address.area) &&
        Objects.equals(this.postCode, address.postCode) &&
        Objects.equals(this.line1, address.line1) &&
        Objects.equals(this.line2, address.line2) &&
        Objects.equals(this.line3, address.line3) &&
        Objects.equals(this.line4, address.line4) &&
        Objects.equals(this.isChangeable, address.isChangeable) &&
        Objects.equals(this.extensions, address.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressId, addressType, countryCode, city, area, postCode, line1, line2, line3, line4, isChangeable, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Address {\n");
    
    sb.append("    addressId: ").append(toIndentedString(addressId)).append("\n");
    sb.append("    addressType: ").append(toIndentedString(addressType)).append("\n");
    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    area: ").append(toIndentedString(area)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    line1: ").append(toIndentedString(line1)).append("\n");
    sb.append("    line2: ").append(toIndentedString(line2)).append("\n");
    sb.append("    line3: ").append(toIndentedString(line3)).append("\n");
    sb.append("    line4: ").append(toIndentedString(line4)).append("\n");
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

