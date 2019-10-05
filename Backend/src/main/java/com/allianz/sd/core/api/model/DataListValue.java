package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Value
 */
@ApiModel(description = "Value")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class DataListValue   {
  @JsonProperty("value")
  private String value = null;

  @JsonProperty("label")
  private String label = null;

  @JsonProperty("orders")
  private BigDecimal orders = null;

  @JsonProperty("arguments")
  private String arguments = null;

  @JsonProperty("validityPeriod")
  private String validityPeriod = null;

  public DataListValue value(String value) {
    this.value = value;
    return this;
  }

  /**
   * The Value of this data item
   * @return value
  **/
  @ApiModelProperty(example = "3", required = true, value = "The Value of this data item")
  @NotNull


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public DataListValue label(String label) {
    this.label = label;
    return this;
  }

  /**
   * The Label to display to the user
   * @return label
  **/
  @ApiModelProperty(example = "Passport", required = true, value = "The Label to display to the user")
  @NotNull


  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public DataListValue orders(BigDecimal orders) {
    this.orders = orders;
    return this;
  }

  /**
   * The order of this data item
   * @return orders
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "The order of this data item")
  @NotNull

  @Valid

  public BigDecimal getOrders() {
    return orders;
  }

  public void setOrders(BigDecimal orders) {
    this.orders = orders;
  }

  public DataListValue arguments(String arguments) {
    this.arguments = arguments;
    return this;
  }

  /**
   * The arguments of this data item(JSON Format)
   * @return arguments
  **/
  @ApiModelProperty(example = "", required = true, value = "The arguments of this data item(JSON Format)")
  @NotNull


  public String getArguments() {
    return arguments;
  }

  public void setArguments(String arguments) {
    this.arguments = arguments;
  }

  public DataListValue validityPeriod(String validityPeriod) {
    this.validityPeriod = validityPeriod;
    return this;
  }

  /**
   * This allows to select the values of a datalist depending on their validity period (StartDate/EndDate)
   * @return validityPeriod
  **/
  @ApiModelProperty(example = "2018-01-01", required = true, value = "This allows to select the values of a datalist depending on their validity period (StartDate/EndDate)")
  @NotNull


  public String getValidityPeriod() {
    return validityPeriod;
  }

  public void setValidityPeriod(String validityPeriod) {
    this.validityPeriod = validityPeriod;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataListValue dataListValue = (DataListValue) o;
    return Objects.equals(this.value, dataListValue.value) &&
        Objects.equals(this.label, dataListValue.label) &&
        Objects.equals(this.orders, dataListValue.orders) &&
        Objects.equals(this.arguments, dataListValue.arguments) &&
        Objects.equals(this.validityPeriod, dataListValue.validityPeriod);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, label, orders, arguments, validityPeriod);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataListValue {\n");
    
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("    label: ").append(toIndentedString(label)).append("\n");
    sb.append("    orders: ").append(toIndentedString(orders)).append("\n");
    sb.append("    arguments: ").append(toIndentedString(arguments)).append("\n");
    sb.append("    validityPeriod: ").append(toIndentedString(validityPeriod)).append("\n");
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

