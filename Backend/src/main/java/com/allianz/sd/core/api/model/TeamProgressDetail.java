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
 * TeamProgressDetail
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class TeamProgressDetail   {
  @JsonProperty("AgentID")
  private String agentID = null;

  @JsonProperty("AgentName")
  private String agentName = null;

  @JsonProperty("JobGrade")
  private String jobGrade = null;

  /**
   * AppUseMode
   */
  public enum AppUseModeEnum {
    AG("AG"),
    
    AL("AL"),
    
    MANAGER("Manager"),
    
    SUPERVISOR("Supervisor");

    private String value;

    AppUseModeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AppUseModeEnum fromValue(String text) {
      for (AppUseModeEnum b : AppUseModeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("AppUseMode")
  private AppUseModeEnum appUseMode = null;

  @JsonProperty("Drilldown")
  private Boolean drilldown = null;

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

  @JsonProperty("Activities")
  private String activities = null;

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

  public TeamProgressDetail agentID(String agentID) {
    this.agentID = agentID;
    return this;
  }

  /**
   * AgentID
   * @return agentID
  **/
  @ApiModelProperty(example = "1", required = true, value = "AgentID")
  @NotNull


  public String getAgentID() {
    return agentID;
  }

  public void setAgentID(String agentID) {
    this.agentID = agentID;
  }

  public TeamProgressDetail agentName(String agentName) {
    this.agentName = agentName;
    return this;
  }

  /**
   * AgentName
   * @return agentName
  **/
  @ApiModelProperty(example = "titan", required = true, value = "AgentName")
  @NotNull


  public String getAgentName() {
    return agentName;
  }

  public void setAgentName(String agentName) {
    this.agentName = agentName;
  }

  public TeamProgressDetail jobGrade(String jobGrade) {
    this.jobGrade = jobGrade;
    return this;
  }

  /**
   * JobGrade
   * @return jobGrade
  **/
  @ApiModelProperty(example = "SM", required = true, value = "JobGrade")
  @NotNull


  public String getJobGrade() {
    return jobGrade;
  }

  public void setJobGrade(String jobGrade) {
    this.jobGrade = jobGrade;
  }

  public TeamProgressDetail appUseMode(AppUseModeEnum appUseMode) {
    this.appUseMode = appUseMode;
    return this;
  }

  /**
   * AppUseMode
   * @return appUseMode
  **/
  @ApiModelProperty(example = "AG", required = true, value = "AppUseMode")

  public AppUseModeEnum getAppUseMode() {
    return appUseMode;
  }

  public void setAppUseMode(AppUseModeEnum appUseMode) {
    this.appUseMode = appUseMode;
  }

  public TeamProgressDetail drilldown(Boolean drilldown) {
    this.drilldown = drilldown;
    return this;
  }

  /**
   * Drilldown
   * @return drilldown
  **/
  @ApiModelProperty(example = "false", required = true, value = "Drilldown")
  @NotNull


  public Boolean isDrilldown() {
    return drilldown;
  }

  public void setDrilldown(Boolean drilldown) {
    this.drilldown = drilldown;
  }

  public TeamProgressDetail dataType(DataTypeEnum dataType) {
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

  public TeamProgressDetail timeBase(TimeBaseEnum timeBase) {
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

  public TeamProgressDetail activities(String activities) {
    this.activities = activities;
    return this;
  }

  /**
   * Activity
   * @return activities
  **/
  @ApiModelProperty(example = "F,SB", required = true, value = "Activity")
  @NotNull


  public String getActivities() {
    return activities;
  }

  public void setActivities(String activities) {
    this.activities = activities;
  }

  public TeamProgressDetail goal(BigDecimal goal) {
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

  public TeamProgressDetail forecast(BigDecimal forecast) {
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

  public TeamProgressDetail actual(BigDecimal actual) {
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

  public TeamProgressDetail shortfall(BigDecimal shortfall) {
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

  public TeamProgressDetail extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public TeamProgressDetail addExtensionsItem(Extension extensionsItem) {
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
    TeamProgressDetail teamProgressDetail = (TeamProgressDetail) o;
    return Objects.equals(this.agentID, teamProgressDetail.agentID) &&
        Objects.equals(this.agentName, teamProgressDetail.agentName) &&
        Objects.equals(this.jobGrade, teamProgressDetail.jobGrade) &&
        Objects.equals(this.appUseMode, teamProgressDetail.appUseMode) &&
        Objects.equals(this.drilldown, teamProgressDetail.drilldown) &&
        Objects.equals(this.dataType, teamProgressDetail.dataType) &&
        Objects.equals(this.timeBase, teamProgressDetail.timeBase) &&
        Objects.equals(this.activities, teamProgressDetail.activities) &&
        Objects.equals(this.goal, teamProgressDetail.goal) &&
        Objects.equals(this.forecast, teamProgressDetail.forecast) &&
        Objects.equals(this.actual, teamProgressDetail.actual) &&
        Objects.equals(this.shortfall, teamProgressDetail.shortfall) &&
        Objects.equals(this.extensions, teamProgressDetail.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agentID, agentName, jobGrade, appUseMode, drilldown, dataType, timeBase, activities, goal, forecast, actual, shortfall, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TeamProgressDetail {\n");
    
    sb.append("    agentID: ").append(toIndentedString(agentID)).append("\n");
    sb.append("    agentName: ").append(toIndentedString(agentName)).append("\n");
    sb.append("    jobGrade: ").append(toIndentedString(jobGrade)).append("\n");
    sb.append("    appUseMode: ").append(toIndentedString(appUseMode)).append("\n");
    sb.append("    drilldown: ").append(toIndentedString(drilldown)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    timeBase: ").append(toIndentedString(timeBase)).append("\n");
    sb.append("    activities: ").append(toIndentedString(activities)).append("\n");
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

