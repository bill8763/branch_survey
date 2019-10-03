package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * AgencyPlanMaster
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class AgencyPlanMaster   {
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

  @JsonProperty("Goal")
  private BigDecimal goal = null;

  @JsonProperty("Forecast")
  private BigDecimal forecast = null;

  @JsonProperty("Actual")
  private BigDecimal actual = null;

  @JsonProperty("Plan")
  private BigDecimal plan = null;

  public AgencyPlanMaster dataType(DataTypeEnum dataType) {
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

  public AgencyPlanMaster goal(BigDecimal goal) {
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

  public AgencyPlanMaster forecast(BigDecimal forecast) {
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

  public AgencyPlanMaster actual(BigDecimal actual) {
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

  public AgencyPlanMaster plan(BigDecimal plan) {
    this.plan = plan;
    return this;
  }

  /**
   * Plan
   * @return plan
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Plan")
  @NotNull

  @Valid

  public BigDecimal getPlan() {
    return plan;
  }

  public void setPlan(BigDecimal plan) {
    this.plan = plan;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AgencyPlanMaster agencyPlanMaster = (AgencyPlanMaster) o;
    return Objects.equals(this.dataType, agencyPlanMaster.dataType) &&
        Objects.equals(this.goal, agencyPlanMaster.goal) &&
        Objects.equals(this.forecast, agencyPlanMaster.forecast) &&
        Objects.equals(this.actual, agencyPlanMaster.actual) &&
        Objects.equals(this.plan, agencyPlanMaster.plan);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataType, goal, forecast, actual, plan);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AgencyPlanMaster {\n");
    
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    goal: ").append(toIndentedString(goal)).append("\n");
    sb.append("    forecast: ").append(toIndentedString(forecast)).append("\n");
    sb.append("    actual: ").append(toIndentedString(actual)).append("\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
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

