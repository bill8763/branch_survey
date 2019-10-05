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
 * GoalExpectedGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-04T02:40:41.764Z")

public class GoalExpectedGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("GoalExpected")
  @Valid
  private List<GoalExpecteds> goalExpected = new ArrayList<GoalExpecteds>();

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public GoalExpectedGetResponse lastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
    return this;
  }

  /**
   * lastUpdateTime
   * @return lastUpdateTime
   **/
  @ApiModelProperty(example = "string", required = true, value = "lastUpdateTime")
  @NotNull


  public String getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  public GoalExpectedGetResponse goalExpected(List<GoalExpecteds> goalExpected) {
    this.goalExpected = goalExpected;
    return this;
  }

  public GoalExpectedGetResponse addGoalExpectedItem(GoalExpecteds goalExpectedItem) {
    this.goalExpected.add(goalExpectedItem);
    return this;
  }

  /**
   * Get goalExpected
   * @return goalExpected
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<GoalExpecteds> getGoalExpected() {
    return goalExpected;
  }

  public void setGoalExpected(List<GoalExpecteds> goalExpected) {
    this.goalExpected = goalExpected;
  }

  public GoalExpectedGetResponse extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public GoalExpectedGetResponse addExtensionsItem(Extension extensionsItem) {
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GoalExpectedGetResponse goalExpectedGetResponse = (GoalExpectedGetResponse) o;
    return Objects.equals(this.lastUpdateTime, goalExpectedGetResponse.lastUpdateTime) &&
            Objects.equals(this.goalExpected, goalExpectedGetResponse.goalExpected) &&
            Objects.equals(this.extensions, goalExpectedGetResponse.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, goalExpected, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalExpectedGetResponse {\n");

    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    goalExpected: ").append(toIndentedString(goalExpected)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
