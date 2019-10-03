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
 * CustomerProfileGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class CustomerProfileGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("customerInfos")
  @Valid
  private List<Person> customerInfos = new ArrayList<Person>();

  @JsonProperty("deletedPersonIds")
  @Valid
  private List<BigDecimal> deletedPersonIds = new ArrayList<BigDecimal>();

  public CustomerProfileGetResponse lastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
    return this;
  }

  /**
   * lastet pull data time
   * @return lastUpdateTime
  **/
  @ApiModelProperty(required = true, value = "lastet pull data time")
  @NotNull


  public String getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  public CustomerProfileGetResponse customerInfos(List<Person> customerInfos) {
    this.customerInfos = customerInfos;
    return this;
  }

  public CustomerProfileGetResponse addCustomerInfosItem(Person customerInfosItem) {
    this.customerInfos.add(customerInfosItem);
    return this;
  }

  /**
   * Customer pull data 
   * @return customerInfos
  **/
  @ApiModelProperty(required = true, value = "Customer pull data ")
  @NotNull

  @Valid

  public List<Person> getCustomerInfos() {
    return customerInfos;
  }

  public void setCustomerInfos(List<Person> customerInfos) {
    this.customerInfos = customerInfos;
  }

  public CustomerProfileGetResponse deletedPersonIds(List<BigDecimal> deletedPersonIds) {
    this.deletedPersonIds = deletedPersonIds;
    return this;
  }

  public CustomerProfileGetResponse addDeletedPersonIdsItem(BigDecimal deletedPersonIdsItem) {
    this.deletedPersonIds.add(deletedPersonIdsItem);
    return this;
  }

  /**
   * delete of customer data
   * @return deletedPersonIds
  **/
  @ApiModelProperty(required = true, value = "delete of customer data")
  @NotNull

  @Valid

  public List<BigDecimal> getDeletedPersonIds() {
    return deletedPersonIds;
  }

  public void setDeletedPersonIds(List<BigDecimal> deletedPersonIds) {
    this.deletedPersonIds = deletedPersonIds;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerProfileGetResponse customerProfileGetResponse = (CustomerProfileGetResponse) o;
    return Objects.equals(this.lastUpdateTime, customerProfileGetResponse.lastUpdateTime) &&
        Objects.equals(this.customerInfos, customerProfileGetResponse.customerInfos) &&
        Objects.equals(this.deletedPersonIds, customerProfileGetResponse.deletedPersonIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, customerInfos, deletedPersonIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CustomerProfileGetResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    customerInfos: ").append(toIndentedString(customerInfos)).append("\n");
    sb.append("    deletedPersonIds: ").append(toIndentedString(deletedPersonIds)).append("\n");
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

