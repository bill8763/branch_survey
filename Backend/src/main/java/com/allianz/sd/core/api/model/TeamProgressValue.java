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
 * TeamProgressValue
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class TeamProgressValue   {
  /**
   * DataType of value
   */
  public enum DataTypeEnum {
    FYFC("FYFC"),
    
    ANP("ANP"),
    
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

  /**
   * TimeBase of value
   */
  public enum TimeBaseEnum {
    MONTH("Month"),
    
    QUARTER("Quarter"),
    
    YEAR("Year");

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

  @JsonProperty("Goal")
  private BigDecimal goal = null;

  @JsonProperty("Forecast")
  private BigDecimal forecast = null;

  @JsonProperty("Actual")
  private BigDecimal actual = null;

  @JsonProperty("Shortfall")
  private BigDecimal shortfall = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public TeamProgressValue dataType(DataTypeEnum dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * DataType of value
   * @return dataType
  **/
  @ApiModelProperty(example = "FYFC", required = true, value = "DataType of value")
  @NotNull


  public DataTypeEnum getDataType() {
    return dataType;
  }

  public void setDataType(DataTypeEnum dataType) {
    this.dataType = dataType;
  }

  public TeamProgressValue timeBase(TimeBaseEnum timeBase) {
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

  public TeamProgressValue goal(BigDecimal goal) {
    this.goal = goal;
    return this;
  }

  /**
   * Goal
   * @return goal
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Goal")
  @NotNull

  @Valid

  public BigDecimal getGoal() {
    return goal;
  }

  public void setGoal(BigDecimal goal) {
    this.goal = goal;
  }

  public TeamProgressValue forecast(BigDecimal forecast) {
    this.forecast = forecast;
    return this;
  }

  /**
   * Forecast
   * @return forecast
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Forecast")
  @NotNull

  @Valid

  public BigDecimal getForecast() {
    return forecast;
  }

  public void setForecast(BigDecimal forecast) {
    this.forecast = forecast;
  }

  public TeamProgressValue actual(BigDecimal actual) {
    this.actual = actual;
    return this;
  }

  /**
   * Actual
   * @return actual
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Actual")
  @NotNull

  @Valid

  public BigDecimal getActual() {
    return actual;
  }

  public void setActual(BigDecimal actual) {
    this.actual = actual;
  }

  public TeamProgressValue shortfall(BigDecimal shortfall) {
    this.shortfall = shortfall;
    return this;
  }

  /**
   * Shortfall
   * @return shortfall
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Shortfall")
  @NotNull

  @Valid

  public BigDecimal getShortfall() {
    return shortfall;
  }

  public void setShortfall(BigDecimal shortfall) {
    this.shortfall = shortfall;
  }

  public TeamProgressValue extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public TeamProgressValue addExtensionsItem(Extension extensionsItem) {
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
    TeamProgressValue teamProgressValue = (TeamProgressValue) o;
    return Objects.equals(this.dataType, teamProgressValue.dataType) &&
        Objects.equals(this.timeBase, teamProgressValue.timeBase) &&
        Objects.equals(this.goal, teamProgressValue.goal) &&
        Objects.equals(this.forecast, teamProgressValue.forecast) &&
        Objects.equals(this.actual, teamProgressValue.actual) &&
        Objects.equals(this.shortfall, teamProgressValue.shortfall) &&
        Objects.equals(this.extensions, teamProgressValue.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataType, timeBase, goal, forecast, actual, shortfall, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TeamProgressValue {\n");
    
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    timeBase: ").append(toIndentedString(timeBase)).append("\n");
    sb.append("    goal: ").append(toIndentedString(goal)).append("\n");
    sb.append("    forecast: ").append(toIndentedString(forecast)).append("\n");
    sb.append("    actual: ").append(toIndentedString(actual)).append("\n");
    sb.append("    shortfall: ").append(toIndentedString(shortfall)).append("\n");
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

