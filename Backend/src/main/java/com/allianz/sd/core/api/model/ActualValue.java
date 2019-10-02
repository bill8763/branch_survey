package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ActualValue
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-10T08:32:22.991Z")

public class ActualValue   {
  @JsonProperty("Month")
  private BigDecimal month = null;

  @JsonProperty("PerformanceType")
  private String performanceType = null;

  /**
   * sales of type
   */
  public enum DataTypeEnum {
    FYFC("FYFC"),
    
    ANP("ANP"),
    
    NEWBUSINESSCASE("NewBusinessCase"),
    
    MANPOWER("Manpower"),
    
    RECRUITMENT("Recruitment");

    private String value;

    DataTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DataTypeEnum fromValue(String text) {
      for (DataTypeEnum b : DataTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("DataType")
  private DataTypeEnum dataType = null;

  @JsonProperty("Value")
  private BigDecimal value = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public ActualValue month(BigDecimal month) {
    this.month = month;
    return this;
  }

  /**
   * Month
   * minimum: 1
   * maximum: 12
   * @return month
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "Month")
  @NotNull

  @Valid
@DecimalMin("1") @DecimalMax("12") 
  public BigDecimal getMonth() {
    return month;
  }

  public void setMonth(BigDecimal month) {
    this.month = month;
  }

  public ActualValue performanceType(String performanceType) {
    this.performanceType = performanceType;
    return this;
  }

  /**
   * PerformanceType of value
   * @return performanceType
  **/
  @ApiModelProperty(example = "P", required = true, value = "PerformanceType of value")
  @NotNull


  public String getPerformanceType() {
    return performanceType;
  }

  public void setPerformanceType(String performanceType) {
    this.performanceType = performanceType;
  }

  public ActualValue dataType(DataTypeEnum dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * sales of type
   * @return dataType
  **/
  @ApiModelProperty(example = "FYFC", required = true, value = "sales of type")
  @NotNull


  public DataTypeEnum getDataType() {
    return dataType;
  }

  public void setDataType(DataTypeEnum dataType) {
    this.dataType = dataType;
  }

  public ActualValue value(BigDecimal value) {
    this.value = value;
    return this;
  }

  /**
   * value
   * @return value
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "value")
  @NotNull

  @Valid
  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  public ActualValue extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public ActualValue addExtensionsItem(Extension extensionsItem) {
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
    ActualValue actualValue = (ActualValue) o;
    return Objects.equals(this.month, actualValue.month) &&
        Objects.equals(this.performanceType, actualValue.performanceType) &&
        Objects.equals(this.dataType, actualValue.dataType) &&
        Objects.equals(this.value, actualValue.value) &&
        Objects.equals(this.extensions, actualValue.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(month, performanceType, dataType, value, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActualValue {\n");
    
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
    sb.append("    performanceType: ").append(toIndentedString(performanceType)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

