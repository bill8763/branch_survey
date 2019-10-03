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
 * AgencyPlanDetailInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class AgencyPlanDetailInfo   {
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

  @JsonProperty("IsApprove")
  private Boolean isApprove = null;

  @JsonProperty("Drilldown")
  private Boolean drilldown = null;

  /**
   * DataType of value
   */
  public enum DataTypeEnum {
    FYFC("FYFC"),
    
    ANP("ANP");

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

  @JsonProperty("Manpower")
  private BigDecimal manpower = null;

  @JsonProperty("Recruitment")
  private BigDecimal recruitment = null;

  @JsonProperty("CaseCount")
  private BigDecimal caseCount = null;

  @JsonProperty("PerCase")
  private BigDecimal perCase = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public AgencyPlanDetailInfo agentID(String agentID) {
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

  public AgencyPlanDetailInfo agentName(String agentName) {
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

  public AgencyPlanDetailInfo jobGrade(String jobGrade) {
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

  public AgencyPlanDetailInfo appUseMode(AppUseModeEnum appUseMode) {
    this.appUseMode = appUseMode;
    return this;
  }

  /**
   * AppUseMode
   * @return appUseMode
  **/
  @ApiModelProperty(example = "AG", required = true, value = "AppUseMode")
  @NotNull


  public AppUseModeEnum getAppUseMode() {
    return appUseMode;
  }

  public void setAppUseMode(AppUseModeEnum appUseMode) {
    this.appUseMode = appUseMode;
  }

  public AgencyPlanDetailInfo isApprove(Boolean isApprove) {
    this.isApprove = isApprove;
    return this;
  }

  /**
   * IsApprove
   * @return isApprove
  **/
  @ApiModelProperty(example = "true", required = true, value = "IsApprove")
  @NotNull


  public Boolean isIsApprove() {
    return isApprove;
  }

  public void setIsApprove(Boolean isApprove) {
    this.isApprove = isApprove;
  }

  public AgencyPlanDetailInfo drilldown(Boolean drilldown) {
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

  public AgencyPlanDetailInfo dataType(DataTypeEnum dataType) {
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

  public AgencyPlanDetailInfo goal(BigDecimal goal) {
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

  public AgencyPlanDetailInfo forecast(BigDecimal forecast) {
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

  public AgencyPlanDetailInfo actual(BigDecimal actual) {
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

  public AgencyPlanDetailInfo plan(BigDecimal plan) {
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

  public AgencyPlanDetailInfo manpower(BigDecimal manpower) {
    this.manpower = manpower;
    return this;
  }

  /**
   * Manpower
   * @return manpower
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Manpower")
  @NotNull

  @Valid

  public BigDecimal getManpower() {
    return manpower;
  }

  public void setManpower(BigDecimal manpower) {
    this.manpower = manpower;
  }

  public AgencyPlanDetailInfo recruitment(BigDecimal recruitment) {
    this.recruitment = recruitment;
    return this;
  }

  /**
   * Recruitment
   * @return recruitment
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Recruitment")
  @NotNull

  @Valid

  public BigDecimal getRecruitment() {
    return recruitment;
  }

  public void setRecruitment(BigDecimal recruitment) {
    this.recruitment = recruitment;
  }

  public AgencyPlanDetailInfo caseCount(BigDecimal caseCount) {
    this.caseCount = caseCount;
    return this;
  }

  /**
   * CaseCount
   * @return caseCount
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "CaseCount")
  @NotNull

  @Valid

  public BigDecimal getCaseCount() {
    return caseCount;
  }

  public void setCaseCount(BigDecimal caseCount) {
    this.caseCount = caseCount;
  }

  public AgencyPlanDetailInfo perCase(BigDecimal perCase) {
    this.perCase = perCase;
    return this;
  }

  /**
   * PerCase
   * @return perCase
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "PerCase")
  @NotNull

  @Valid

  public BigDecimal getPerCase() {
    return perCase;
  }

  public void setPerCase(BigDecimal perCase) {
    this.perCase = perCase;
  }

  public AgencyPlanDetailInfo extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public AgencyPlanDetailInfo addExtensionsItem(Extension extensionsItem) {
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
    AgencyPlanDetailInfo agencyPlanDetailInfo = (AgencyPlanDetailInfo) o;
    return Objects.equals(this.agentID, agencyPlanDetailInfo.agentID) &&
        Objects.equals(this.agentName, agencyPlanDetailInfo.agentName) &&
        Objects.equals(this.jobGrade, agencyPlanDetailInfo.jobGrade) &&
        Objects.equals(this.appUseMode, agencyPlanDetailInfo.appUseMode) &&
        Objects.equals(this.isApprove, agencyPlanDetailInfo.isApprove) &&
        Objects.equals(this.drilldown, agencyPlanDetailInfo.drilldown) &&
        Objects.equals(this.dataType, agencyPlanDetailInfo.dataType) &&
        Objects.equals(this.goal, agencyPlanDetailInfo.goal) &&
        Objects.equals(this.forecast, agencyPlanDetailInfo.forecast) &&
        Objects.equals(this.actual, agencyPlanDetailInfo.actual) &&
        Objects.equals(this.plan, agencyPlanDetailInfo.plan) &&
        Objects.equals(this.manpower, agencyPlanDetailInfo.manpower) &&
        Objects.equals(this.recruitment, agencyPlanDetailInfo.recruitment) &&
        Objects.equals(this.caseCount, agencyPlanDetailInfo.caseCount) &&
        Objects.equals(this.perCase, agencyPlanDetailInfo.perCase) &&
        Objects.equals(this.extensions, agencyPlanDetailInfo.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agentID, agentName, jobGrade, appUseMode, isApprove, drilldown, dataType, goal, forecast, actual, plan, manpower, recruitment, caseCount, perCase, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AgencyPlanDetailInfo {\n");
    
    sb.append("    agentID: ").append(toIndentedString(agentID)).append("\n");
    sb.append("    agentName: ").append(toIndentedString(agentName)).append("\n");
    sb.append("    jobGrade: ").append(toIndentedString(jobGrade)).append("\n");
    sb.append("    appUseMode: ").append(toIndentedString(appUseMode)).append("\n");
    sb.append("    isApprove: ").append(toIndentedString(isApprove)).append("\n");
    sb.append("    drilldown: ").append(toIndentedString(drilldown)).append("\n");
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    goal: ").append(toIndentedString(goal)).append("\n");
    sb.append("    forecast: ").append(toIndentedString(forecast)).append("\n");
    sb.append("    actual: ").append(toIndentedString(actual)).append("\n");
    sb.append("    plan: ").append(toIndentedString(plan)).append("\n");
    sb.append("    manpower: ").append(toIndentedString(manpower)).append("\n");
    sb.append("    recruitment: ").append(toIndentedString(recruitment)).append("\n");
    sb.append("    caseCount: ").append(toIndentedString(caseCount)).append("\n");
    sb.append("    perCase: ").append(toIndentedString(perCase)).append("\n");
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

