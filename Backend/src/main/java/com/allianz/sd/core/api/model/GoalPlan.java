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
 * GoalPlan
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class GoalPlan   {
  @JsonProperty("TimeBase")
  private String timeBase = null;

  @JsonProperty("Values")
  @Valid
  private List<GoalPlanValue> values = new ArrayList<GoalPlanValue>();

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public GoalPlan timeBase(String timeBase) {
    this.timeBase = timeBase;
    return this;
  }

  /**
   * split of timebase
   * @return timeBase
  **/
  @ApiModelProperty(example = "Month", required = true, value = "split of timebase")
  @NotNull


  public String getTimeBase() {
    return timeBase;
  }

  public void setTimeBase(String timeBase) {
    this.timeBase = timeBase;
  }

  public GoalPlan values(List<GoalPlanValue> values) {
    this.values = values;
    return this;
  }

  public GoalPlan addValuesItem(GoalPlanValue valuesItem) {
    this.values.add(valuesItem);
    return this;
  }

  /**
   * Datatype of value
   * @return values
  **/
  @ApiModelProperty(required = true, value = "Datatype of value")
  @NotNull

  @Valid

  public List<GoalPlanValue> getValues() {
    return values;
  }

  public void setValues(List<GoalPlanValue> values) {
    this.values = values;
  }

  public GoalPlan extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public GoalPlan addExtensionsItem(Extension extensionsItem) {
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
    GoalPlan goalPlan = (GoalPlan) o;
    return Objects.equals(this.timeBase, goalPlan.timeBase) &&
        Objects.equals(this.values, goalPlan.values) &&
        Objects.equals(this.extensions, goalPlan.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timeBase, values, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalPlan {\n");
    
    sb.append("    timeBase: ").append(toIndentedString(timeBase)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
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

