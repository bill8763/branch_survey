package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ApproveGoalRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class ApproveGoalRequest   {
  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  @JsonProperty("AgentID")
  private String agentID = null;

  @JsonProperty("isApprove")
  private Boolean isApprove = null;

  @JsonProperty("comment")
  private String comment = null;

  public ApproveGoalRequest dataYear(BigDecimal dataYear) {
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

  public ApproveGoalRequest agentID(String agentID) {
    this.agentID = agentID;
    return this;
  }

  /**
   * AgrntID
   * @return agentID
  **/
  @ApiModelProperty(example = "xxxxxx", required = true, value = "AgrntID")
  @NotNull


  public String getAgentID() {
    return agentID;
  }

  public void setAgentID(String agentID) {
    this.agentID = agentID;
  }

  public ApproveGoalRequest isApprove(Boolean isApprove) {
    this.isApprove = isApprove;
    return this;
  }

  /**
   * approve is or not
   * @return isApprove
  **/
  @ApiModelProperty(example = "false", required = true, value = "approve is or not")
  @NotNull


  public Boolean isIsApprove() {
    return isApprove;
  }

  public void setIsApprove(Boolean isApprove) {
    this.isApprove = isApprove;
  }

  public ApproveGoalRequest comment(String comment) {
    this.comment = comment;
    return this;
  }

  /**
   * comment of approve
   * @return comment
  **/
  @ApiModelProperty(example = "Goal is too low", required = true, value = "comment of approve")
  @NotNull


  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApproveGoalRequest approveGoalRequest = (ApproveGoalRequest) o;
    return Objects.equals(this.dataYear, approveGoalRequest.dataYear) &&
        Objects.equals(this.agentID, approveGoalRequest.agentID) &&
        Objects.equals(this.isApprove, approveGoalRequest.isApprove) &&
        Objects.equals(this.comment, approveGoalRequest.comment);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataYear, agentID, isApprove, comment);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApproveGoalRequest {\n");
    
    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    agentID: ").append(toIndentedString(agentID)).append("\n");
    sb.append("    isApprove: ").append(toIndentedString(isApprove)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
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

