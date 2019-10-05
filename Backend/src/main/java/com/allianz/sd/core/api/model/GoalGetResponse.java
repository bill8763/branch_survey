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
 * GoalGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T07:48:54.804Z")

public class GoalGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("Goals")
  @Valid
  private List<Goal> goals = new ArrayList<Goal>();

  public GoalGetResponse lastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
    return this;
  }

  /**
   * lastet pull data time
   * @return lastUpdateTime
  **/
  @ApiModelProperty(required = true, value = "lastet pull data time")
  @NotNull


  public String getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  public GoalGetResponse goals(List<Goal> goals) {
    this.goals = goals;
    return this;
  }

  public GoalGetResponse addGoalsItem(Goal goalsItem) {
    this.goals.add(goalsItem);
    return this;
  }

  /**
   * Actual pull data 
   * @return goals
  **/
  @ApiModelProperty(required = true, value = "Actual pull data ")
  @NotNull

  @Valid
@Size(min = 1,max=2)
  public List<Goal> getGoals() {
    return goals;
  }

  public void setGoals(List<Goal> goals) {
    this.goals = goals;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GoalGetResponse goalGetResponse = (GoalGetResponse) o;
    return Objects.equals(this.lastUpdateTime, goalGetResponse.lastUpdateTime) &&
        Objects.equals(this.goals, goalGetResponse.goals);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, goals);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalGetResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    goals: ").append(toIndentedString(goals)).append("\n");
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

