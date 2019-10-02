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
 * GoalValue
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class GoalValue   {
  @JsonProperty("DataType")
  private String dataType = null;

  @JsonProperty("Value")
  private String value = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public GoalValue dataType(String dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * PERSON_FYFC / PERSON_ANP / ....
   * @return dataType
  **/
  @ApiModelProperty(example = "PERSON_FYFC / PERSON_ANP / ....", required = true, value = "PERSON_FYFC / PERSON_ANP / ....")
  @NotNull

  @Valid

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public GoalValue value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Datatype of value
   * @return value
  **/
  @ApiModelProperty(example = "10000", required = true, value = "Datatype of value")
  @NotNull


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public GoalValue extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public GoalValue addExtensionsItem(Extension extensionsItem) {
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
    GoalValue goalValue = (GoalValue) o;
    return Objects.equals(this.dataType, goalValue.dataType) &&
        Objects.equals(this.value, goalValue.value) &&
        Objects.equals(this.extensions, goalValue.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataType, value, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalValue {\n");
    
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

