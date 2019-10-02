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
 * GoalExpected
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-02T05:29:22.249Z")

public class GoalExpected   {
  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  @JsonProperty("FYFC")
  private BigDecimal FYFC = null;

  @JsonProperty("ANP")
  private BigDecimal ANP = null;

  @JsonProperty("Recruitments")
  @Valid
  private List<GoalExpectedValue> recruitments = new ArrayList<GoalExpectedValue>();

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  @JsonProperty("synchDetail")
  private SynchDetailNoDelete synchDetail = null;

  public GoalExpected dataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
    return this;
  }

  /**
   * DataYear
   * minimum: 2019
   * maximum: 9999
   * @return dataYear
   **/
  @ApiModelProperty(example = "2019.0", required = true, value = "DataYear")
  @NotNull

  @Valid
  @DecimalMin("2019") @DecimalMax("9999")
  public BigDecimal getDataYear() {
    return dataYear;
  }

  public void setDataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
  }

  public GoalExpected FYFC(BigDecimal FYFC) {
    this.FYFC = FYFC;
    return this;
  }

  /**
   * FYFC
   * @return FYFC
   **/
  @ApiModelProperty(example = "10.0", required = true, value = "FYFC")
  @NotNull

  @Valid

  public BigDecimal getFYFC() {
    return FYFC;
  }

  public void setFYFC(BigDecimal FYFC) {
    this.FYFC = FYFC;
  }

  public GoalExpected ANP(BigDecimal ANP) {
    this.ANP = ANP;
    return this;
  }

  /**
   * ANP
   * @return ANP
   **/
  @ApiModelProperty(example = "10.0", required = true, value = "ANP")
  @NotNull

  @Valid

  public BigDecimal getANP() {
    return ANP;
  }

  public void setANP(BigDecimal ANP) {
    this.ANP = ANP;
  }

  public GoalExpected recruitments(List<GoalExpectedValue> recruitments) {
    this.recruitments = recruitments;
    return this;
  }

  public GoalExpected addRecruitmentsItem(GoalExpectedValue recruitmentsItem) {
    this.recruitments.add(recruitmentsItem);
    return this;
  }

  /**
   * Datatype of value
   * @return recruitments
   **/
  @ApiModelProperty(required = true, value = "Datatype of value")
  @NotNull

  @Valid

  public List<GoalExpectedValue> getRecruitments() {
    return recruitments;
  }

  public void setRecruitments(List<GoalExpectedValue> recruitments) {
    this.recruitments = recruitments;
  }

  public GoalExpected extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public GoalExpected addExtensionsItem(Extension extensionsItem) {
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

  public GoalExpected synchDetail(SynchDetailNoDelete synchDetail) {
    this.synchDetail = synchDetail;
    return this;
  }

  /**
   * Get synchDetail
   * @return synchDetail
   **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public SynchDetailNoDelete getSynchDetail() {
    return synchDetail;
  }

  public void setSynchDetail(SynchDetailNoDelete synchDetail) {
    this.synchDetail = synchDetail;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GoalExpected goalExpected = (GoalExpected) o;
    return Objects.equals(this.dataYear, goalExpected.dataYear) &&
            Objects.equals(this.FYFC, goalExpected.FYFC) &&
            Objects.equals(this.ANP, goalExpected.ANP) &&
            Objects.equals(this.recruitments, goalExpected.recruitments) &&
            Objects.equals(this.extensions, goalExpected.extensions) &&
            Objects.equals(this.synchDetail, goalExpected.synchDetail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataYear, FYFC, ANP, recruitments, extensions, synchDetail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalExpected {\n");

    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    FYFC: ").append(toIndentedString(FYFC)).append("\n");
    sb.append("    ANP: ").append(toIndentedString(ANP)).append("\n");
    sb.append("    recruitments: ").append(toIndentedString(recruitments)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
    sb.append("    synchDetail: ").append(toIndentedString(synchDetail)).append("\n");
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

