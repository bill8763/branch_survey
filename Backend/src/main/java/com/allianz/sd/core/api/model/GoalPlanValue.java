package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GoalPlanValue
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class GoalPlanValue   {
  /**
   * PerformanceType of value
   */
  public enum PerformanceTypeEnum {
    P("P"),
    
    T("T");

    private String value;

    PerformanceTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PerformanceTypeEnum fromValue(String text) {
      for (PerformanceTypeEnum b : PerformanceTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("PerformanceType")
  private PerformanceTypeEnum performanceType = null;

  @JsonProperty("Month")
  private BigDecimal month = null;

  @JsonProperty("Value")
  private String value = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public GoalPlanValue performanceType(PerformanceTypeEnum performanceType) {
    this.performanceType = performanceType;
    return this;
  }

  /**
   * PerformanceType of value
   * @return performanceType
  **/
  @ApiModelProperty(example = "P / T", required = true, value = "PerformanceType of value")
  @NotNull


  public PerformanceTypeEnum getPerformanceType() {
    return performanceType;
  }

  public void setPerformanceType(PerformanceTypeEnum performanceType) {
    this.performanceType = performanceType;
  }

  public GoalPlanValue month(BigDecimal month) {
    this.month = month;
    return this;
  }

  /**
   * split of month
   * minimum: 1
   * maximum: 12
   * @return month
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "split of month")
  @NotNull

  @Valid
@DecimalMin("1") @DecimalMax("12") 
  public BigDecimal getMonth() {
    return month;
  }

  public void setMonth(BigDecimal month) {
    this.month = month;
  }

  public GoalPlanValue value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Datatype of value
   * @return value
  **/
  @ApiModelProperty(example = "20", required = true, value = "Datatype of value")
  @NotNull


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public GoalPlanValue extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public GoalPlanValue addExtensionsItem(Extension extensionsItem) {
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
    GoalPlanValue goalPlanValue = (GoalPlanValue) o;
    return Objects.equals(this.performanceType, goalPlanValue.performanceType) &&
        Objects.equals(this.month, goalPlanValue.month) &&
        Objects.equals(this.value, goalPlanValue.value) &&
        Objects.equals(this.extensions, goalPlanValue.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(performanceType, month, value, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalPlanValue {\n");
    
    sb.append("    performanceType: ").append(toIndentedString(performanceType)).append("\n");
    sb.append("    month: ").append(toIndentedString(month)).append("\n");
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

