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
 * GoalExpecteds
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-02T05:43:10.342Z")

public class GoalExpecteds   {
  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  @JsonProperty("FYFC")
  private BigDecimal FYFC = null;

  @JsonProperty("ANP")
  private BigDecimal ANP = null;

  @JsonProperty("Recruitments")
  @Valid
  private List<GoalExpectedValue> recruitments = null;

  @JsonProperty("synchDetail")
  private SynchDetailNoDelete synchDetail = null;

  public GoalExpecteds dataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
    return this;
  }

  /**
   * FYFC
   * minimum: 2019
   * maximum: 9999
   * @return dataYear
  **/
  @ApiModelProperty(example = "2019.0", value = "FYFC")

  @Valid
@DecimalMin("2019") @DecimalMax("9999") 
  public BigDecimal getDataYear() {
    return dataYear;
  }

  public void setDataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
  }

  public GoalExpecteds FYFC(BigDecimal FYFC) {
    this.FYFC = FYFC;
    return this;
  }

  /**
   * FYFC
   * @return FYFC
  **/
  @ApiModelProperty(example = "10.0", value = "FYFC")

  @Valid

  public BigDecimal getFYFC() {
    return FYFC;
  }

  public void setFYFC(BigDecimal FYFC) {
    this.FYFC = FYFC;
  }

  public GoalExpecteds ANP(BigDecimal ANP) {
    this.ANP = ANP;
    return this;
  }

  /**
   * ANP
   * @return ANP
  **/
  @ApiModelProperty(example = "10.0", value = "ANP")

  @Valid

  public BigDecimal getANP() {
    return ANP;
  }

  public void setANP(BigDecimal ANP) {
    this.ANP = ANP;
  }

  public GoalExpecteds recruitments(List<GoalExpectedValue> recruitments) {
    this.recruitments = recruitments;
    return this;
  }

  public GoalExpecteds addRecruitmentsItem(GoalExpectedValue recruitmentsItem) {
    if (this.recruitments == null) {
      this.recruitments = new ArrayList<GoalExpectedValue>();
    }
    this.recruitments.add(recruitmentsItem);
    return this;
  }

  /**
   * Datatype of value
   * @return recruitments
  **/
  @ApiModelProperty(value = "Datatype of value")

  @Valid
@Size(min=4) 
  public List<GoalExpectedValue> getRecruitments() {
    return recruitments;
  }

  public void setRecruitments(List<GoalExpectedValue> recruitments) {
    this.recruitments = recruitments;
  }

  public GoalExpecteds synchDetail(SynchDetailNoDelete synchDetail) {
    this.synchDetail = synchDetail;
    return this;
  }

  /**
   * Get synchDetail
   * @return synchDetail
  **/
  @ApiModelProperty(value = "")

  @Valid

  public SynchDetailNoDelete getSynchDetail() {
    return synchDetail;
  }

  public void setSynchDetail(SynchDetailNoDelete synchDetail) {
    this.synchDetail = synchDetail;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GoalExpecteds goalExpecteds = (GoalExpecteds) o;
    return Objects.equals(this.dataYear, goalExpecteds.dataYear) &&
        Objects.equals(this.FYFC, goalExpecteds.FYFC) &&
        Objects.equals(this.ANP, goalExpecteds.ANP) &&
        Objects.equals(this.recruitments, goalExpecteds.recruitments) &&
        Objects.equals(this.synchDetail, goalExpecteds.synchDetail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataYear, FYFC, ANP, recruitments, synchDetail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalExpecteds {\n");
    
    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    FYFC: ").append(toIndentedString(FYFC)).append("\n");
    sb.append("    ANP: ").append(toIndentedString(ANP)).append("\n");
    sb.append("    recruitments: ").append(toIndentedString(recruitments)).append("\n");
    sb.append("    synchDetail: ").append(toIndentedString(synchDetail)).append("\n");
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

