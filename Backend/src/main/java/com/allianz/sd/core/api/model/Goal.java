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
 * Goal
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-02T07:33:54.628Z")

public class Goal   {
  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  @JsonProperty("GoalSetting")
  private GoalSetting goalSetting = null;

  @JsonProperty("GoalValue")
  @Valid
  private List<GoalValue> goalValue = new ArrayList<GoalValue>();

  @JsonProperty("GoalPlan")
  private GoalPlan goalPlan = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public Goal dataYear(BigDecimal dataYear) {
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

  public Goal goalSetting(GoalSetting goalSetting) {
    this.goalSetting = goalSetting;
    return this;
  }

  /**
   * Get goalSetting
   * @return goalSetting
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public GoalSetting getGoalSetting() {
    return goalSetting;
  }

  public void setGoalSetting(GoalSetting goalSetting) {
    this.goalSetting = goalSetting;
  }

  public Goal goalValue(List<GoalValue> goalValue) {
    this.goalValue = goalValue;
    return this;
  }

  public Goal addGoalValueItem(GoalValue goalValueItem) {
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
@Size(min=1) 
  public List<GoalValue> getGoalValue() {
    return goalValue;
  }

  public void setGoalValue(List<GoalValue> goalValue) {
    this.goalValue = goalValue;
  }

  public Goal goalPlan(GoalPlan goalPlan) {
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

  public Goal extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Goal addExtensionsItem(Extension extensionsItem) {
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
    Goal goal = (Goal) o;
    return Objects.equals(this.dataYear, goal.dataYear) &&
        Objects.equals(this.goalSetting, goal.goalSetting) &&
        Objects.equals(this.goalValue, goal.goalValue) &&
        Objects.equals(this.goalPlan, goal.goalPlan) &&
        Objects.equals(this.extensions, goal.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataYear, goalSetting, goalValue, goalPlan, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Goal {\n");
    
    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    goalSetting: ").append(toIndentedString(goalSetting)).append("\n");
    sb.append("    goalValue: ").append(toIndentedString(goalValue)).append("\n");
    sb.append("    goalPlan: ").append(toIndentedString(goalPlan)).append("\n");
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

