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
 * AgencyPlan
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-04T07:38:43.675Z")

public class AgencyPlan   {
  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  @JsonProperty("CompletionRate")
  private BigDecimal completionRate = null;

  @JsonProperty("Values")
  @Valid
  private List<AgencyPlanMaster> values = new ArrayList<AgencyPlanMaster>();

  @JsonProperty("DirectUnit")
  @Valid
  private List<AgencyPlanDetailInfo> directUnit = new ArrayList<AgencyPlanDetailInfo>();

  @JsonProperty("InDirectUnit")
  @Valid
  private List<AgencyPlanDetailInfo> inDirectUnit = new ArrayList<AgencyPlanDetailInfo>();

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public AgencyPlan dataYear(BigDecimal dataYear) {
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

  public AgencyPlan completionRate(BigDecimal completionRate) {
    this.completionRate = completionRate;
    return this;
  }

  /**
   * CompletionRate of year
   * maximum: 100
   * @return completionRate
   **/
  @ApiModelProperty(example = "30.0", value = "CompletionRate of year")

  @Valid
  @DecimalMax("100")
  public BigDecimal getCompletionRate() {
    return completionRate;
  }

  public void setCompletionRate(BigDecimal completionRate) {
    this.completionRate = completionRate;
  }

  public AgencyPlan values(List<AgencyPlanMaster> values) {
    this.values = values;
    return this;
  }

  public AgencyPlan addValuesItem(AgencyPlanMaster valuesItem) {
    this.values.add(valuesItem);
    return this;
  }

  /**
   * values of array
   * @return values
   **/
  @ApiModelProperty(required = true, value = "values of array")
  @NotNull

  @Valid
  public List<AgencyPlanMaster> getValues() {
    return values;
  }

  public void setValues(List<AgencyPlanMaster> values) {
    this.values = values;
  }

  public AgencyPlan directUnit(List<AgencyPlanDetailInfo> directUnit) {
    this.directUnit = directUnit;
    return this;
  }

  public AgencyPlan addDirectUnitItem(AgencyPlanDetailInfo directUnitItem) {
    this.directUnit.add(directUnitItem);
    return this;
  }

  /**
   * DirectUnit
   * @return directUnit
   **/
  @ApiModelProperty(required = true, value = "DirectUnit")
  @NotNull

  @Valid

  public List<AgencyPlanDetailInfo> getDirectUnit() {
    return directUnit;
  }

  public void setDirectUnit(List<AgencyPlanDetailInfo> directUnit) {
    this.directUnit = directUnit;
  }

  public AgencyPlan inDirectUnit(List<AgencyPlanDetailInfo> inDirectUnit) {
    this.inDirectUnit = inDirectUnit;
    return this;
  }

  public AgencyPlan addInDirectUnitItem(AgencyPlanDetailInfo inDirectUnitItem) {
    this.inDirectUnit.add(inDirectUnitItem);
    return this;
  }

  /**
   * DirectUnit
   * @return inDirectUnit
   **/
  @ApiModelProperty(required = true, value = "DirectUnit")
  @NotNull

  @Valid

  public List<AgencyPlanDetailInfo> getInDirectUnit() {
    return inDirectUnit;
  }

  public void setInDirectUnit(List<AgencyPlanDetailInfo> inDirectUnit) {
    this.inDirectUnit = inDirectUnit;
  }

  public AgencyPlan extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public AgencyPlan addExtensionsItem(Extension extensionsItem) {
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
    AgencyPlan agencyPlan = (AgencyPlan) o;
    return Objects.equals(this.dataYear, agencyPlan.dataYear) &&
            Objects.equals(this.completionRate, agencyPlan.completionRate) &&
            Objects.equals(this.values, agencyPlan.values) &&
            Objects.equals(this.directUnit, agencyPlan.directUnit) &&
            Objects.equals(this.inDirectUnit, agencyPlan.inDirectUnit) &&
            Objects.equals(this.extensions, agencyPlan.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataYear, completionRate, values, directUnit, inDirectUnit, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AgencyPlan {\n");

    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    completionRate: ").append(toIndentedString(completionRate)).append("\n");
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
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
