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
 * SubmitGoalResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class SubmitGoalResponse   {
  @JsonProperty("success")
  private Boolean success = null;

  @JsonProperty("errorCode")
  private String errorCode = null;

  @JsonProperty("Goals")
  @Valid
  private List<Goal> goals = new ArrayList<Goal>();

  public SubmitGoalResponse success(Boolean success) {
    this.success = success;
    return this;
  }

  /**
   * success of not
   * @return success
  **/
  @ApiModelProperty(example = "true", required = true, value = "success of not")
  @NotNull


  public Boolean isSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public SubmitGoalResponse errorCode(String errorCode) {
    this.errorCode = errorCode;
    return this;
  }

  /**
   * fail of errorCode
   * @return errorCode
  **/
  @ApiModelProperty(example = "123", value = "fail of errorCode")


  public String getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public SubmitGoalResponse goals(List<Goal> goals) {
    this.goals = goals;
    return this;
  }

  public SubmitGoalResponse addGoalsItem(Goal goalsItem) {
    this.goals.add(goalsItem);
    return this;
  }

  /**
   * Get goals
   * @return goals
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

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
    SubmitGoalResponse submitGoalResponse = (SubmitGoalResponse) o;
    return Objects.equals(this.success, submitGoalResponse.success) &&
        Objects.equals(this.errorCode, submitGoalResponse.errorCode) &&
        Objects.equals(this.goals, submitGoalResponse.goals);
  }

  @Override
  public int hashCode() {
    return Objects.hash(success, errorCode, goals);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubmitGoalResponse {\n");
    
    sb.append("    success: ").append(toIndentedString(success)).append("\n");
    sb.append("    errorCode: ").append(toIndentedString(errorCode)).append("\n");
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

