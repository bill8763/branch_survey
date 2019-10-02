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
 * Actual
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-02T07:02:49.579Z")

public class Actual   {
  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  /**
   * TimeBase of value
   */
  public enum TimeBaseEnum {
    MONTH("Month");

    private String value;

    TimeBaseEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TimeBaseEnum fromValue(String text) {
      for (TimeBaseEnum b : TimeBaseEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("TimeBase")
  private TimeBaseEnum timeBase = null;

  @JsonProperty("Values")
  @Valid
  private List<ActualValue> values = new ArrayList<ActualValue>();

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public Actual dataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
    return this;
  }

  /**
   * year of data
   * minimum: 2019
   * maximum: 9999
   * @return dataYear
   **/
  @ApiModelProperty(example = "2019.0", required = true, value = "year of data")
  @NotNull

  @Valid
  @DecimalMin("2019") @DecimalMax("9999")
  public BigDecimal getDataYear() {
    return dataYear;
  }

  public void setDataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
  }

  public Actual timeBase(TimeBaseEnum timeBase) {
    this.timeBase = timeBase;
    return this;
  }

  /**
   * TimeBase of value
   * @return timeBase
   **/
  @ApiModelProperty(example = "Month", required = true, value = "TimeBase of value")
  @NotNull


  public TimeBaseEnum getTimeBase() {
    return timeBase;
  }

  public void setTimeBase(TimeBaseEnum timeBase) {
    this.timeBase = timeBase;
  }

  public Actual values(List<ActualValue> values) {
    this.values = values;
    return this;
  }

  public Actual addValuesItem(ActualValue valuesItem) {
    this.values.add(valuesItem);
    return this;
  }

  /**
   * actual data of year
   * @return values
   **/
  @ApiModelProperty(required = true, value = "actual data of year")
  @NotNull

  @Valid

  public List<ActualValue> getValues() {
    return values;
  }

  public void setValues(List<ActualValue> values) {
    this.values = values;
  }

  public Actual extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Actual addExtensionsItem(Extension extensionsItem) {
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Actual actual = (Actual) o;
    return Objects.equals(this.dataYear, actual.dataYear) &&
            Objects.equals(this.timeBase, actual.timeBase) &&
            Objects.equals(this.values, actual.values) &&
            Objects.equals(this.extensions, actual.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataYear, timeBase, values, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Actual {\n");

    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    timeBase: ").append(toIndentedString(timeBase)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
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
