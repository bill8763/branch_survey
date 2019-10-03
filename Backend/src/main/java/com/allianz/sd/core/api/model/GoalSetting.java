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
 * GoalSetting
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class GoalSetting   {
  @JsonProperty("IsNeedSetting")
  private Boolean isNeedSetting = null;

  @JsonProperty("IsCurrent")
  private Boolean isCurrent = null;

  @JsonProperty("IsFirstTime")
  private Boolean isFirstTime = null;

  /**
   * goal status(N : No setting/W : waiting/P : pending/A : pproved)
   */
  public enum StatusEnum {
    W("W"),
    
    P("P"),
    
    A("A"),
    
    R("R"),
    
    N("N");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static StatusEnum fromValue(String text) {
      for (StatusEnum b : StatusEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("Status")
  private StatusEnum status = null;

  @JsonProperty("GoalSetMonth")
  private BigDecimal goalSetMonth = null;

  @JsonProperty("Remainingdays")
  private BigDecimal remainingdays = null;

  @JsonProperty("PersonnelGoalApplicableYM")
  private BigDecimal personnelGoalApplicableYM = null;

  @JsonProperty("TeamGoalApplicableYM")
  private BigDecimal teamGoalApplicableYM = null;

  @JsonProperty("SupervisorComment")
  private String supervisorComment = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public GoalSetting isNeedSetting(Boolean isNeedSetting) {
    this.isNeedSetting = isNeedSetting;
    return this;
  }

  /**
   * need to setting the goal or not
   * @return isNeedSetting
  **/
  @ApiModelProperty(example = "true", required = true, value = "need to setting the goal or not")
  @NotNull


  public Boolean isIsNeedSetting() {
    return isNeedSetting;
  }

  public void setIsNeedSetting(Boolean isNeedSetting) {
    this.isNeedSetting = isNeedSetting;
  }

  public GoalSetting isCurrent(Boolean isCurrent) {
    this.isCurrent = isCurrent;
    return this;
  }

  /**
   * now year is current year
   * @return isCurrent
  **/
  @ApiModelProperty(example = "true", required = true, value = "now year is current year")
  @NotNull


  public Boolean isIsCurrent() {
    return isCurrent;
  }

  public void setIsCurrent(Boolean isCurrent) {
    this.isCurrent = isCurrent;
  }

  public GoalSetting isFirstTime(Boolean isFirstTime) {
    this.isFirstTime = isFirstTime;
    return this;
  }

  /**
   * this goal is first time to setting
   * @return isFirstTime
  **/
  @ApiModelProperty(example = "true", required = true, value = "this goal is first time to setting")
  @NotNull


  public Boolean isIsFirstTime() {
    return isFirstTime;
  }

  public void setIsFirstTime(Boolean isFirstTime) {
    this.isFirstTime = isFirstTime;
  }

  public GoalSetting status(StatusEnum status) {
    this.status = status;
    return this;
  }

  /**
   * goal status(N : No setting/W : waiting/P : pending/A : pproved)
   * @return status
  **/
  @ApiModelProperty(example = "w", required = true, value = "goal status(N : No setting/W : waiting/P : pending/A : pproved)")
  @NotNull


  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

  public GoalSetting goalSetMonth(BigDecimal goalSetMonth) {
    this.goalSetMonth = goalSetMonth;
    return this;
  }

  /**
   * GoalSetMonth
   * minimum: 1
   * maximum: 12
   * @return goalSetMonth
  **/
  @ApiModelProperty(example = "5.0", required = true, value = "GoalSetMonth")
  @NotNull

  @Valid
  public BigDecimal getGoalSetMonth() {
    return goalSetMonth;
  }

  public void setGoalSetMonth(BigDecimal goalSetMonth) {
    this.goalSetMonth = goalSetMonth;
  }

  public GoalSetting remainingdays(BigDecimal remainingdays) {
    this.remainingdays = remainingdays;
    return this;
  }

  /**
   * Remainingdays
   * maximum: 365
   * @return remainingdays
  **/
  @ApiModelProperty(example = "15.0", required = true, value = "Remainingdays")
  @NotNull

  @Valid
 @DecimalMax("365") 
  public BigDecimal getRemainingdays() {
    return remainingdays;
  }

  public void setRemainingdays(BigDecimal remainingdays) {
    this.remainingdays = remainingdays;
  }

  public GoalSetting personnelGoalApplicableYM(BigDecimal personnelGoalApplicableYM) {
    this.personnelGoalApplicableYM = personnelGoalApplicableYM;
    return this;
  }

  /**
   * PersonnelGoalApplicableYM
   * @return personnelGoalApplicableYM
  **/
  @ApiModelProperty(example = "1.0", value = "PersonnelGoalApplicableYM")

  @Valid

  public BigDecimal getPersonnelGoalApplicableYM() {
    return personnelGoalApplicableYM;
  }

  public void setPersonnelGoalApplicableYM(BigDecimal personnelGoalApplicableYM) {
    this.personnelGoalApplicableYM = personnelGoalApplicableYM;
  }

  public GoalSetting teamGoalApplicableYM(BigDecimal teamGoalApplicableYM) {
    this.teamGoalApplicableYM = teamGoalApplicableYM;
    return this;
  }

  /**
   * TeamGoalApplicableYM
   * @return teamGoalApplicableYM
  **/
  @ApiModelProperty(example = "3.0", value = "TeamGoalApplicableYM")

  @Valid

  public BigDecimal getTeamGoalApplicableYM() {
    return teamGoalApplicableYM;
  }

  public void setTeamGoalApplicableYM(BigDecimal teamGoalApplicableYM) {
    this.teamGoalApplicableYM = teamGoalApplicableYM;
  }

  public GoalSetting supervisorComment(String supervisorComment) {
    this.supervisorComment = supervisorComment;
    return this;
  }

  /**
   * SupervisorComment
   * @return supervisorComment
  **/
  @ApiModelProperty(example = "SupervisorComment", value = "SupervisorComment")


  public String getSupervisorComment() {
    return supervisorComment;
  }

  public void setSupervisorComment(String supervisorComment) {
    this.supervisorComment = supervisorComment;
  }

  public GoalSetting extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public GoalSetting addExtensionsItem(Extension extensionsItem) {
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
    GoalSetting goalSetting = (GoalSetting) o;
    return Objects.equals(this.isNeedSetting, goalSetting.isNeedSetting) &&
        Objects.equals(this.isCurrent, goalSetting.isCurrent) &&
        Objects.equals(this.isFirstTime, goalSetting.isFirstTime) &&
        Objects.equals(this.status, goalSetting.status) &&
        Objects.equals(this.goalSetMonth, goalSetting.goalSetMonth) &&
        Objects.equals(this.remainingdays, goalSetting.remainingdays) &&
        Objects.equals(this.personnelGoalApplicableYM, goalSetting.personnelGoalApplicableYM) &&
        Objects.equals(this.teamGoalApplicableYM, goalSetting.teamGoalApplicableYM) &&
        Objects.equals(this.supervisorComment, goalSetting.supervisorComment) &&
        Objects.equals(this.extensions, goalSetting.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isNeedSetting, isCurrent, isFirstTime, status, goalSetMonth, remainingdays, personnelGoalApplicableYM, teamGoalApplicableYM, supervisorComment, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalSetting {\n");
    
    sb.append("    isNeedSetting: ").append(toIndentedString(isNeedSetting)).append("\n");
    sb.append("    isCurrent: ").append(toIndentedString(isCurrent)).append("\n");
    sb.append("    isFirstTime: ").append(toIndentedString(isFirstTime)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    goalSetMonth: ").append(toIndentedString(goalSetMonth)).append("\n");
    sb.append("    remainingdays: ").append(toIndentedString(remainingdays)).append("\n");
    sb.append("    personnelGoalApplicableYM: ").append(toIndentedString(personnelGoalApplicableYM)).append("\n");
    sb.append("    teamGoalApplicableYM: ").append(toIndentedString(teamGoalApplicableYM)).append("\n");
    sb.append("    supervisorComment: ").append(toIndentedString(supervisorComment)).append("\n");
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

