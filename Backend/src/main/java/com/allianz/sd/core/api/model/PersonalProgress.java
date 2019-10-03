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
 * PersonalProgress
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class PersonalProgress   {
  @JsonProperty("Values")
  @Valid
  private List<PersonalProgressValue> values = null;

  public PersonalProgress values(List<PersonalProgressValue> values) {
    this.values = values;
    return this;
  }

  public PersonalProgress addValuesItem(PersonalProgressValue valuesItem) {
    if (this.values == null) {
      this.values = new ArrayList<PersonalProgressValue>();
    }
    this.values.add(valuesItem);
    return this;
  }

  /**
   * PersonalProgressValue
   * @return values
  **/
  @ApiModelProperty(value = "PersonalProgressValue")

  @Valid

  public List<PersonalProgressValue> getValues() {
    return values;
  }

  public void setValues(List<PersonalProgressValue> values) {
    this.values = values;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonalProgress personalProgress = (PersonalProgress) o;
    return Objects.equals(this.values, personalProgress.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(values);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalProgress {\n");
    
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
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

