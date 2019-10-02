package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * GoalExpectedPutResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class GoalExpectedPutResponse   {
  @JsonProperty("synchStatus")
  private Boolean synchStatus = null;

  @JsonProperty("updateRecordsNumber")
  private Integer updateRecordsNumber = null;

  public GoalExpectedPutResponse synchStatus(Boolean synchStatus) {
    this.synchStatus = synchStatus;
    return this;
  }

  /**
   * backend update data is success. true=successed completly, false=one or more customers failed to update
   * @return synchStatus
  **/
  @ApiModelProperty(example = "true", required = true, value = "backend update data is success. true=successed completly, false=one or more customers failed to update")
  @NotNull


  public Boolean isSynchStatus() {
    return synchStatus;
  }

  public void setSynchStatus(Boolean synchStatus) {
    this.synchStatus = synchStatus;
  }

  public GoalExpectedPutResponse updateRecordsNumber(Integer updateRecordsNumber) {
    this.updateRecordsNumber = updateRecordsNumber;
    return this;
  }

  /**
   * backend update data total records
   * @return updateRecordsNumber
  **/
  @ApiModelProperty(example = "100", required = true, value = "backend update data total records")
  @NotNull


  public Integer getUpdateRecordsNumber() {
    return updateRecordsNumber;
  }

  public void setUpdateRecordsNumber(Integer updateRecordsNumber) {
    this.updateRecordsNumber = updateRecordsNumber;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GoalExpectedPutResponse goalExpectedPutResponse = (GoalExpectedPutResponse) o;
    return Objects.equals(this.synchStatus, goalExpectedPutResponse.synchStatus) &&
        Objects.equals(this.updateRecordsNumber, goalExpectedPutResponse.updateRecordsNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(synchStatus, updateRecordsNumber);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GoalExpectedPutResponse {\n");
    
    sb.append("    synchStatus: ").append(toIndentedString(synchStatus)).append("\n");
    sb.append("    updateRecordsNumber: ").append(toIndentedString(updateRecordsNumber)).append("\n");
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

