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
 * ModelConfiguration
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class ModelConfiguration   {
  @JsonProperty("IsCurrent")
  private Boolean isCurrent = null;

  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  @JsonProperty("PerformanceSettlementMonth")
  private BigDecimal performanceSettlementMonth = null;

  @JsonProperty("WorkingQuarter")
  private BigDecimal workingQuarter = null;

  @JsonProperty("WorkingMonth")
  private BigDecimal workingMonth = null;

  @JsonProperty("QuarterStartMonth")
  private BigDecimal quarterStartMonth = null;

  @JsonProperty("QuarterEndMonth")
  private BigDecimal quarterEndMonth = null;

  @JsonProperty("InitialPreCaseFyfc")
  private BigDecimal initialPreCaseFyfc = null;

  @JsonProperty("FyfcCovertAnpRate")
  private BigDecimal fyfcCovertAnpRate = null;

  @JsonProperty("ProgressDayPointsLimit")
  private BigDecimal progressDayPointsLimit = null;

  @JsonProperty("GoalSettingActivityProcMode")
  private String goalSettingActivityProcMode = null;

  @JsonProperty("GoalAndPlanDifferenceLimit")
  private BigDecimal goalAndPlanDifferenceLimit = null;

  @JsonProperty("FindConvertPointBase")
  private BigDecimal findConvertPointBase = null;

  @JsonProperty("ScheduleConvertPointBase")
  private BigDecimal scheduleConvertPointBase = null;

  @JsonProperty("MeetConvertPointBase")
  private BigDecimal meetConvertPointBase = null;

  @JsonProperty("SubmitConvertPointBase")
  private BigDecimal submitConvertPointBase = null;

  @JsonProperty("InforceConvertPointBase")
  private BigDecimal inforceConvertPointBase = null;

  @JsonProperty("InforceConvertFindRate")
  private BigDecimal inforceConvertFindRate = null;

  @JsonProperty("InforceConvertScheduleRate")
  private BigDecimal inforceConvertScheduleRate = null;

  @JsonProperty("InforceConvertMeetRate")
  private BigDecimal inforceConvertMeetRate = null;

  @JsonProperty("InforceConvertSubmitRate")
  private BigDecimal inforceConvertSubmitRate = null;

  @JsonProperty("ProgressBarControlMode")
  private String progressBarControlMode = null;

  @JsonProperty("NowToYearEndDays")
  private BigDecimal nowToYearEndDays = null;

  @JsonProperty("MonthQuantityOfYear")
  private BigDecimal monthQuantityOfYear = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public ModelConfiguration isCurrent(Boolean isCurrent) {
    this.isCurrent = isCurrent;
    return this;
  }

  /**
   * Is current year configuration
   * @return isCurrent
  **/
  @ApiModelProperty(example = "true", required = true, value = "Is current year configuration")
  @NotNull


  public Boolean isIsCurrent() {
    return isCurrent;
  }

  public void setIsCurrent(Boolean isCurrent) {
    this.isCurrent = isCurrent;
  }

  public ModelConfiguration dataYear(BigDecimal dataYear) {
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

  public ModelConfiguration performanceSettlementMonth(BigDecimal performanceSettlementMonth) {
    this.performanceSettlementMonth = performanceSettlementMonth;
    return this;
  }

  /**
   * calc actual of month
   * minimum: 1
   * @return performanceSettlementMonth
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "calc actual of month")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getPerformanceSettlementMonth() {
    return performanceSettlementMonth;
  }

  public void setPerformanceSettlementMonth(BigDecimal performanceSettlementMonth) {
    this.performanceSettlementMonth = performanceSettlementMonth;
  }

  public ModelConfiguration workingQuarter(BigDecimal workingQuarter) {
    this.workingQuarter = workingQuarter;
    return this;
  }

  /**
   * working quarter
   * minimum: 1
   * maximum: 4
   * @return workingQuarter
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "working quarter")
  @NotNull

  @Valid
@DecimalMin("0") @DecimalMax("4")
  public BigDecimal getWorkingQuarter() {
    return workingQuarter;
  }

  public void setWorkingQuarter(BigDecimal workingQuarter) {
    this.workingQuarter = workingQuarter;
  }

  public ModelConfiguration workingMonth(BigDecimal workingMonth) {
    this.workingMonth = workingMonth;
    return this;
  }

  /**
   * working month
   * minimum: 0
   * maximum: 12
   * @return workingMonth
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "working month")
  @NotNull

  @Valid
@DecimalMin("0") @DecimalMax("12") 
  public BigDecimal getWorkingMonth() {
    return workingMonth;
  }

  public void setWorkingMonth(BigDecimal workingMonth) {
    this.workingMonth = workingMonth;
  }

  public ModelConfiguration quarterStartMonth(BigDecimal quarterStartMonth) {
    this.quarterStartMonth = quarterStartMonth;
    return this;
  }

  /**
   * Quarter start month
   * minimum: 1
   * maximum: 10
   * @return quarterStartMonth
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "Quarter start month")
  @NotNull

  @Valid
@DecimalMin("1") @DecimalMax("10") 
  public BigDecimal getQuarterStartMonth() {
    return quarterStartMonth;
  }

  public void setQuarterStartMonth(BigDecimal quarterStartMonth) {
    this.quarterStartMonth = quarterStartMonth;
  }

  public ModelConfiguration quarterEndMonth(BigDecimal quarterEndMonth) {
    this.quarterEndMonth = quarterEndMonth;
    return this;
  }

  /**
   * Quarter end month
   * minimum: 3
   * maximum: 12
   * @return quarterEndMonth
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "Quarter end month")
  @NotNull

  @Valid
@DecimalMin("3") @DecimalMax("12") 
  public BigDecimal getQuarterEndMonth() {
    return quarterEndMonth;
  }

  public void setQuarterEndMonth(BigDecimal quarterEndMonth) {
    this.quarterEndMonth = quarterEndMonth;
  }

  public ModelConfiguration initialPreCaseFyfc(BigDecimal initialPreCaseFyfc) {
    this.initialPreCaseFyfc = initialPreCaseFyfc;
    return this;
  }

  /**
   * Default Avg. FYFC per case
   * minimum: 1
   * @return initialPreCaseFyfc
  **/
  @ApiModelProperty(example = "20.0", required = true, value = "Default Avg. FYFC per case")
  @NotNull

  @Valid
@DecimalMin("1")
  public BigDecimal getInitialPreCaseFyfc() {
    return initialPreCaseFyfc;
  }

  public void setInitialPreCaseFyfc(BigDecimal initialPreCaseFyfc) {
    this.initialPreCaseFyfc = initialPreCaseFyfc;
  }

  public ModelConfiguration fyfcCovertAnpRate(BigDecimal fyfcCovertAnpRate) {
    this.fyfcCovertAnpRate = fyfcCovertAnpRate;
    return this;
  }

  /**
   * FyfcCovertAnpRate
   * minimum: 0
   * @return fyfcCovertAnpRate
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "FyfcCovertAnpRate")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getFyfcCovertAnpRate() {
    return fyfcCovertAnpRate;
  }

  public void setFyfcCovertAnpRate(BigDecimal fyfcCovertAnpRate) {
    this.fyfcCovertAnpRate = fyfcCovertAnpRate;
  }

  public ModelConfiguration progressDayPointsLimit(BigDecimal progressDayPointsLimit) {
    this.progressDayPointsLimit = progressDayPointsLimit;
    return this;
  }

  /**
   * ProgressDayPointsLimit
   * minimum: 0
   * @return progressDayPointsLimit
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "ProgressDayPointsLimit")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getProgressDayPointsLimit() {
    return progressDayPointsLimit;
  }

  public void setProgressDayPointsLimit(BigDecimal progressDayPointsLimit) {
    this.progressDayPointsLimit = progressDayPointsLimit;
  }

  public ModelConfiguration goalSettingActivityProcMode(String goalSettingActivityProcMode) {
    this.goalSettingActivityProcMode = goalSettingActivityProcMode;
    return this;
  }

  /**
   * GoalSettingActivityProcMode
   * @return goalSettingActivityProcMode
  **/
  @ApiModelProperty(example = "TWMode", required = true, value = "GoalSettingActivityProcMode")
  @NotNull


  public String getGoalSettingActivityProcMode() {
    return goalSettingActivityProcMode;
  }

  public void setGoalSettingActivityProcMode(String goalSettingActivityProcMode) {
    this.goalSettingActivityProcMode = goalSettingActivityProcMode;
  }

  public ModelConfiguration goalAndPlanDifferenceLimit(BigDecimal goalAndPlanDifferenceLimit) {
    this.goalAndPlanDifferenceLimit = goalAndPlanDifferenceLimit;
    return this;
  }

  /**
   * GoalAndPlanDifferenceLimit
   * minimum: 0
   * @return goalAndPlanDifferenceLimit
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "GoalAndPlanDifferenceLimit")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getGoalAndPlanDifferenceLimit() {
    return goalAndPlanDifferenceLimit;
  }

  public void setGoalAndPlanDifferenceLimit(BigDecimal goalAndPlanDifferenceLimit) {
    this.goalAndPlanDifferenceLimit = goalAndPlanDifferenceLimit;
  }

  public ModelConfiguration findConvertPointBase(BigDecimal findConvertPointBase) {
    this.findConvertPointBase = findConvertPointBase;
    return this;
  }

  /**
   * FindConvertPointBase
   * minimum: 0
   * @return findConvertPointBase
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "FindConvertPointBase")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getFindConvertPointBase() {
    return findConvertPointBase;
  }

  public void setFindConvertPointBase(BigDecimal findConvertPointBase) {
    this.findConvertPointBase = findConvertPointBase;
  }

  public ModelConfiguration scheduleConvertPointBase(BigDecimal scheduleConvertPointBase) {
    this.scheduleConvertPointBase = scheduleConvertPointBase;
    return this;
  }

  /**
   * ScheduleConvertPointBase
   * minimum: 0
   * @return scheduleConvertPointBase
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "ScheduleConvertPointBase")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getScheduleConvertPointBase() {
    return scheduleConvertPointBase;
  }

  public void setScheduleConvertPointBase(BigDecimal scheduleConvertPointBase) {
    this.scheduleConvertPointBase = scheduleConvertPointBase;
  }

  public ModelConfiguration meetConvertPointBase(BigDecimal meetConvertPointBase) {
    this.meetConvertPointBase = meetConvertPointBase;
    return this;
  }

  /**
   * MeetConvertPointBase
   * minimum: 0
   * @return meetConvertPointBase
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "MeetConvertPointBase")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getMeetConvertPointBase() {
    return meetConvertPointBase;
  }

  public void setMeetConvertPointBase(BigDecimal meetConvertPointBase) {
    this.meetConvertPointBase = meetConvertPointBase;
  }

  public ModelConfiguration submitConvertPointBase(BigDecimal submitConvertPointBase) {
    this.submitConvertPointBase = submitConvertPointBase;
    return this;
  }

  /**
   * SubmitConvertPointBase
   * minimum: 0
   * @return submitConvertPointBase
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "SubmitConvertPointBase")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getSubmitConvertPointBase() {
    return submitConvertPointBase;
  }

  public void setSubmitConvertPointBase(BigDecimal submitConvertPointBase) {
    this.submitConvertPointBase = submitConvertPointBase;
  }

  public ModelConfiguration inforceConvertPointBase(BigDecimal inforceConvertPointBase) {
    this.inforceConvertPointBase = inforceConvertPointBase;
    return this;
  }

  /**
   * InforceConvertPointBase
   * minimum: 0
   * @return inforceConvertPointBase
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "InforceConvertPointBase")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getInforceConvertPointBase() {
    return inforceConvertPointBase;
  }

  public void setInforceConvertPointBase(BigDecimal inforceConvertPointBase) {
    this.inforceConvertPointBase = inforceConvertPointBase;
  }

  public ModelConfiguration inforceConvertFindRate(BigDecimal inforceConvertFindRate) {
    this.inforceConvertFindRate = inforceConvertFindRate;
    return this;
  }

  /**
   * InforceConvertFindRate
   * minimum: 0
   * @return inforceConvertFindRate
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "InforceConvertFindRate")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getInforceConvertFindRate() {
    return inforceConvertFindRate;
  }

  public void setInforceConvertFindRate(BigDecimal inforceConvertFindRate) {
    this.inforceConvertFindRate = inforceConvertFindRate;
  }

  public ModelConfiguration inforceConvertScheduleRate(BigDecimal inforceConvertScheduleRate) {
    this.inforceConvertScheduleRate = inforceConvertScheduleRate;
    return this;
  }

  /**
   * InforceConvertScheduleRate
   * minimum: 0
   * @return inforceConvertScheduleRate
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "InforceConvertScheduleRate")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getInforceConvertScheduleRate() {
    return inforceConvertScheduleRate;
  }

  public void setInforceConvertScheduleRate(BigDecimal inforceConvertScheduleRate) {
    this.inforceConvertScheduleRate = inforceConvertScheduleRate;
  }

  public ModelConfiguration inforceConvertMeetRate(BigDecimal inforceConvertMeetRate) {
    this.inforceConvertMeetRate = inforceConvertMeetRate;
    return this;
  }

  /**
   * InforceConvertMeetRate
   * minimum: 0
   * @return inforceConvertMeetRate
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "InforceConvertMeetRate")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getInforceConvertMeetRate() {
    return inforceConvertMeetRate;
  }

  public void setInforceConvertMeetRate(BigDecimal inforceConvertMeetRate) {
    this.inforceConvertMeetRate = inforceConvertMeetRate;
  }

  public ModelConfiguration inforceConvertSubmitRate(BigDecimal inforceConvertSubmitRate) {
    this.inforceConvertSubmitRate = inforceConvertSubmitRate;
    return this;
  }

  /**
   * InforceConvertSubmitRate
   * minimum: 0
   * @return inforceConvertSubmitRate
  **/
  @ApiModelProperty(example = "0.2", required = true, value = "InforceConvertSubmitRate")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getInforceConvertSubmitRate() {
    return inforceConvertSubmitRate;
  }

  public void setInforceConvertSubmitRate(BigDecimal inforceConvertSubmitRate) {
    this.inforceConvertSubmitRate = inforceConvertSubmitRate;
  }

  public ModelConfiguration progressBarControlMode(String progressBarControlMode) {
    this.progressBarControlMode = progressBarControlMode;
    return this;
  }

  /**
   * ProgressBarControlMode
   * @return progressBarControlMode
  **/
  @ApiModelProperty(example = "TWMode", required = true, value = "ProgressBarControlMode")
  @NotNull


  public String getProgressBarControlMode() {
    return progressBarControlMode;
  }

  public void setProgressBarControlMode(String progressBarControlMode) {
    this.progressBarControlMode = progressBarControlMode;
  }

  public ModelConfiguration nowToYearEndDays(BigDecimal nowToYearEndDays) {
    this.nowToYearEndDays = nowToYearEndDays;
    return this;
  }

  /**
   * NowToYearEndDays
   * minimum: 1
   * maximum: 365
   * @return nowToYearEndDays
  **/
  @ApiModelProperty(example = "20.0", required = true, value = "NowToYearEndDays")
  @NotNull

  @Valid
@DecimalMin("1") @DecimalMax("365") 
  public BigDecimal getNowToYearEndDays() {
    return nowToYearEndDays;
  }

  public void setNowToYearEndDays(BigDecimal nowToYearEndDays) {
    this.nowToYearEndDays = nowToYearEndDays;
  }

  public ModelConfiguration monthQuantityOfYear(BigDecimal monthQuantityOfYear) {
    this.monthQuantityOfYear = monthQuantityOfYear;
    return this;
  }

  /**
   * Month Quantity Of Year
   * minimum: 1
   * maximum: 12
   * @return monthQuantityOfYear
  **/
  @ApiModelProperty(example = "12.0", required = true, value = "Month Quantity Of Year")
  @NotNull

  @Valid
@DecimalMin("1") @DecimalMax("12") 
  public BigDecimal getMonthQuantityOfYear() {
    return monthQuantityOfYear;
  }

  public void setMonthQuantityOfYear(BigDecimal monthQuantityOfYear) {
    this.monthQuantityOfYear = monthQuantityOfYear;
  }

  public ModelConfiguration extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public ModelConfiguration addExtensionsItem(Extension extensionsItem) {
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
    ModelConfiguration _configuration = (ModelConfiguration) o;
    return Objects.equals(this.isCurrent, _configuration.isCurrent) &&
        Objects.equals(this.dataYear, _configuration.dataYear) &&
        Objects.equals(this.performanceSettlementMonth, _configuration.performanceSettlementMonth) &&
        Objects.equals(this.workingQuarter, _configuration.workingQuarter) &&
        Objects.equals(this.workingMonth, _configuration.workingMonth) &&
        Objects.equals(this.quarterStartMonth, _configuration.quarterStartMonth) &&
        Objects.equals(this.quarterEndMonth, _configuration.quarterEndMonth) &&
        Objects.equals(this.initialPreCaseFyfc, _configuration.initialPreCaseFyfc) &&
        Objects.equals(this.fyfcCovertAnpRate, _configuration.fyfcCovertAnpRate) &&
        Objects.equals(this.progressDayPointsLimit, _configuration.progressDayPointsLimit) &&
        Objects.equals(this.goalSettingActivityProcMode, _configuration.goalSettingActivityProcMode) &&
        Objects.equals(this.goalAndPlanDifferenceLimit, _configuration.goalAndPlanDifferenceLimit) &&
        Objects.equals(this.findConvertPointBase, _configuration.findConvertPointBase) &&
        Objects.equals(this.scheduleConvertPointBase, _configuration.scheduleConvertPointBase) &&
        Objects.equals(this.meetConvertPointBase, _configuration.meetConvertPointBase) &&
        Objects.equals(this.submitConvertPointBase, _configuration.submitConvertPointBase) &&
        Objects.equals(this.inforceConvertPointBase, _configuration.inforceConvertPointBase) &&
        Objects.equals(this.inforceConvertFindRate, _configuration.inforceConvertFindRate) &&
        Objects.equals(this.inforceConvertScheduleRate, _configuration.inforceConvertScheduleRate) &&
        Objects.equals(this.inforceConvertMeetRate, _configuration.inforceConvertMeetRate) &&
        Objects.equals(this.inforceConvertSubmitRate, _configuration.inforceConvertSubmitRate) &&
        Objects.equals(this.progressBarControlMode, _configuration.progressBarControlMode) &&
        Objects.equals(this.nowToYearEndDays, _configuration.nowToYearEndDays) &&
        Objects.equals(this.monthQuantityOfYear, _configuration.monthQuantityOfYear) &&
        Objects.equals(this.extensions, _configuration.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isCurrent, dataYear, performanceSettlementMonth, workingQuarter, workingMonth, quarterStartMonth, quarterEndMonth, initialPreCaseFyfc, fyfcCovertAnpRate, progressDayPointsLimit, goalSettingActivityProcMode, goalAndPlanDifferenceLimit, findConvertPointBase, scheduleConvertPointBase, meetConvertPointBase, submitConvertPointBase, inforceConvertPointBase, inforceConvertFindRate, inforceConvertScheduleRate, inforceConvertMeetRate, inforceConvertSubmitRate, progressBarControlMode, nowToYearEndDays, monthQuantityOfYear, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelConfiguration {\n");
    
    sb.append("    isCurrent: ").append(toIndentedString(isCurrent)).append("\n");
    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    performanceSettlementMonth: ").append(toIndentedString(performanceSettlementMonth)).append("\n");
    sb.append("    workingQuarter: ").append(toIndentedString(workingQuarter)).append("\n");
    sb.append("    workingMonth: ").append(toIndentedString(workingMonth)).append("\n");
    sb.append("    quarterStartMonth: ").append(toIndentedString(quarterStartMonth)).append("\n");
    sb.append("    quarterEndMonth: ").append(toIndentedString(quarterEndMonth)).append("\n");
    sb.append("    initialPreCaseFyfc: ").append(toIndentedString(initialPreCaseFyfc)).append("\n");
    sb.append("    fyfcCovertAnpRate: ").append(toIndentedString(fyfcCovertAnpRate)).append("\n");
    sb.append("    progressDayPointsLimit: ").append(toIndentedString(progressDayPointsLimit)).append("\n");
    sb.append("    goalSettingActivityProcMode: ").append(toIndentedString(goalSettingActivityProcMode)).append("\n");
    sb.append("    goalAndPlanDifferenceLimit: ").append(toIndentedString(goalAndPlanDifferenceLimit)).append("\n");
    sb.append("    findConvertPointBase: ").append(toIndentedString(findConvertPointBase)).append("\n");
    sb.append("    scheduleConvertPointBase: ").append(toIndentedString(scheduleConvertPointBase)).append("\n");
    sb.append("    meetConvertPointBase: ").append(toIndentedString(meetConvertPointBase)).append("\n");
    sb.append("    submitConvertPointBase: ").append(toIndentedString(submitConvertPointBase)).append("\n");
    sb.append("    inforceConvertPointBase: ").append(toIndentedString(inforceConvertPointBase)).append("\n");
    sb.append("    inforceConvertFindRate: ").append(toIndentedString(inforceConvertFindRate)).append("\n");
    sb.append("    inforceConvertScheduleRate: ").append(toIndentedString(inforceConvertScheduleRate)).append("\n");
    sb.append("    inforceConvertMeetRate: ").append(toIndentedString(inforceConvertMeetRate)).append("\n");
    sb.append("    inforceConvertSubmitRate: ").append(toIndentedString(inforceConvertSubmitRate)).append("\n");
    sb.append("    progressBarControlMode: ").append(toIndentedString(progressBarControlMode)).append("\n");
    sb.append("    nowToYearEndDays: ").append(toIndentedString(nowToYearEndDays)).append("\n");
    sb.append("    monthQuantityOfYear: ").append(toIndentedString(monthQuantityOfYear)).append("\n");
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

