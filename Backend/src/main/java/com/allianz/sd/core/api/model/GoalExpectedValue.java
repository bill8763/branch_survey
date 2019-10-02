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
 * GoalExpectedValue
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class GoalExpectedValue   {
  @JsonProperty("Qarter")
  private BigDecimal qarter = null;

  @JsonProperty("Value")
  private String value = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public GoalExpectedValue qarter(BigDecimal qarter) {
    this.qarter = qarter;
    return this;
  }

  /**
   * split of qarter
   * minimum: 1
   * maximum: 4
   * @return qarter
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "split of qarter")
  @NotNull

  @Valid
@DecimalMin("1") @DecimalMax("4") 
  public BigDecimal getQarter() {
    return qarter;
  }

  public void setQarter(BigDecimal qarter) {
    this.qarter = qarter;
  }

  public GoalExpectedValue value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Datatype of value
   * @return value
  **/
  @ApiModelProperty(example = "20", required = true, value = "Datatype of value")
  @NotNull


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public GoalExpectedValue extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public GoalExpectedValue addExtensionsItem(Extension extensionsItem) {
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
    GoalExpectedValue goalExpectedValue = (GoalExpectedValue) o;
    return Objects.equals(this.qarter, goalExpectedValue.qarter) &&
        Objects.equals(this.value, goalExpectedValue.value) &&
        Objects.equals(this.extensions, goalExpectedValue.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(qarter, value, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalExpectedValue {\n");
    
    sb.append("    qarter: ").append(toIndentedString(qarter)).append("\n");
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

