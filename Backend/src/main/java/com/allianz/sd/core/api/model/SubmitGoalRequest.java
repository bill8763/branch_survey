package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * SubmitGoalRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class SubmitGoalRequest   {
  @JsonProperty("SubmitInfo")
  private SubmitInfo submitInfo = null;

  @JsonProperty("GoalValue")
  @Valid
  private List<GoalValue> goalValue = new ArrayList<GoalValue>();

  @JsonProperty("GoalPlan")
  private GoalPlan goalPlan = null;

  public SubmitGoalRequest submitInfo(SubmitInfo submitInfo) {
    this.submitInfo = submitInfo;
    return this;
  }

  /**
   * Get submitInfo
   * @return submitInfo
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public SubmitInfo getSubmitInfo() {
    return submitInfo;
  }

  public void setSubmitInfo(SubmitInfo submitInfo) {
    this.submitInfo = submitInfo;
  }

  public SubmitGoalRequest goalValue(List<GoalValue> goalValue) {
    this.goalValue = goalValue;
    return this;
  }

  public SubmitGoalRequest addGoalValueItem(GoalValue goalValueItem) {
    this.goalValue.add(goalValueItem);
    return this;
  }

  /**
   * goal setting of value
   * @return goalValue
  **/
  @ApiModelProperty(required = true, value = "goal setting of value")
  @NotNull

  @Valid

  public List<GoalValue> getGoalValue() {
    return goalValue;
  }

  public void setGoalValue(List<GoalValue> goalValue) {
    this.goalValue = goalValue;
  }

  public SubmitGoalRequest goalPlan(GoalPlan goalPlan) {
    this.goalPlan = goalPlan;
    return this;
  }

  /**
   * Get goalPlan
   * @return goalPlan
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public GoalPlan getGoalPlan() {
    return goalPlan;
  }

  public void setGoalPlan(GoalPlan goalPlan) {
    this.goalPlan = goalPlan;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SubmitGoalRequest submitGoalRequest = (SubmitGoalRequest) o;
    return Objects.equals(this.submitInfo, submitGoalRequest.submitInfo) &&
        Objects.equals(this.goalValue, submitGoalRequest.goalValue) &&
        Objects.equals(this.goalPlan, submitGoalRequest.goalPlan);
  }

  @Override
  public int hashCode() {
    return Objects.hash(submitInfo, goalValue, goalPlan);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubmitGoalRequest {\n");
    
    sb.append("    submitInfo: ").append(toIndentedString(submitInfo)).append("\n");
    sb.append("    goalValue: ").append(toIndentedString(goalValue)).append("\n");
    sb.append("    goalPlan: ").append(toIndentedString(goalPlan)).append("\n");
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

