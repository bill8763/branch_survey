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
 * TeamProgress
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class TeamProgress   {
  @JsonProperty("Values")
  @Valid
  private List<TeamProgressValue> values = new ArrayList<TeamProgressValue>();

  @JsonProperty("DirectUnit")
  @Valid
  private List<TeamProgressDetail> directUnit = null;

  @JsonProperty("InDirectUnit")
  @Valid
  private List<TeamProgressDetail> inDirectUnit = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public TeamProgress values(List<TeamProgressValue> values) {
    this.values = values;
    return this;
  }

  public TeamProgress addValuesItem(TeamProgressValue valuesItem) {
    this.values.add(valuesItem);
    return this;
  }

  /**
   * TeamProgressValue
   * @return values
  **/
  @ApiModelProperty(required = true, value = "TeamProgressValue")
  @NotNull

  @Valid

  public List<TeamProgressValue> getValues() {
    return values;
  }

  public void setValues(List<TeamProgressValue> values) {
    this.values = values;
  }

  public TeamProgress directUnit(List<TeamProgressDetail> directUnit) {
    this.directUnit = directUnit;
    return this;
  }

  public TeamProgress addDirectUnitItem(TeamProgressDetail directUnitItem) {
    if (this.directUnit == null) {
      this.directUnit = new ArrayList<TeamProgressDetail>();
    }
    this.directUnit.add(directUnitItem);
    return this;
  }

  /**
   * TeamProgressValue
   * @return directUnit
  **/
  @ApiModelProperty(value = "TeamProgressValue")

  @Valid

  public List<TeamProgressDetail> getDirectUnit() {
    return directUnit;
  }

  public void setDirectUnit(List<TeamProgressDetail> directUnit) {
    this.directUnit = directUnit;
  }

  public TeamProgress inDirectUnit(List<TeamProgressDetail> inDirectUnit) {
    this.inDirectUnit = inDirectUnit;
    return this;
  }

  public TeamProgress addInDirectUnitItem(TeamProgressDetail inDirectUnitItem) {
    if (this.inDirectUnit == null) {
      this.inDirectUnit = new ArrayList<TeamProgressDetail>();
    }
    this.inDirectUnit.add(inDirectUnitItem);
    return this;
  }

  /**
   * TeamProgressValue
   * @return inDirectUnit
  **/
  @ApiModelProperty(value = "TeamProgressValue")

  @Valid

  public List<TeamProgressDetail> getInDirectUnit() {
    return inDirectUnit;
  }

  public void setInDirectUnit(List<TeamProgressDetail> inDirectUnit) {
    this.inDirectUnit = inDirectUnit;
  }

  public TeamProgress extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public TeamProgress addExtensionsItem(Extension extensionsItem) {
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
    TeamProgress teamProgress = (TeamProgress) o;
    return Objects.equals(this.values, teamProgress.values) &&
        Objects.equals(this.directUnit, teamProgress.directUnit) &&
        Objects.equals(this.inDirectUnit, teamProgress.inDirectUnit) &&
        Objects.equals(this.extensions, teamProgress.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values, directUnit, inDirectUnit, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TeamProgress {\n");
    
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
    sb.append("    directUnit: ").append(toIndentedString(directUnit)).append("\n");
    sb.append("    inDirectUnit: ").append(toIndentedString(inDirectUnit)).append("\n");
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

