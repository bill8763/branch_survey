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
 * MessageGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class MessageGetResponse   {
  @JsonProperty("synchStatus")
  private Boolean synchStatus = null;

  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("updateRecordsNumber")
  private Integer updateRecordsNumber = null;

  @JsonProperty("messageInfos")
  @Valid
  private List<Message> messageInfos = new ArrayList<Message>();

  @JsonProperty("deletedMessageIds")
  @Valid
  private List<BigDecimal> deletedMessageIds = new ArrayList<BigDecimal>();

  public MessageGetResponse synchStatus(Boolean synchStatus) {
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

  public MessageGetResponse lastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
    return this;
  }

  /**
   * lastet pull data time
   * @return lastUpdateTime
  **/
  @ApiModelProperty(required = true, value = "lastet pull data time")
  @NotNull


  public String getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  public MessageGetResponse updateRecordsNumber(Integer updateRecordsNumber) {
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

  public MessageGetResponse messageInfos(List<Message> messageInfos) {
    this.messageInfos = messageInfos;
    return this;
  }

  public MessageGetResponse addMessageInfosItem(Message messageInfosItem) {
    this.messageInfos.add(messageInfosItem);
    return this;
  }

  /**
   * synchronized message
   * @return messageInfos
  **/
  @ApiModelProperty(required = true, value = "synchronized message")
  @NotNull

  @Valid

  public List<Message> getMessageInfos() {
    return messageInfos;
  }

  public void setMessageInfos(List<Message> messageInfos) {
    this.messageInfos = messageInfos;
  }

  public MessageGetResponse deletedMessageIds(List<BigDecimal> deletedMessageIds) {
    this.deletedMessageIds = deletedMessageIds;
    return this;
  }

  public MessageGetResponse addDeletedMessageIdsItem(BigDecimal deletedMessageIdsItem) {
    this.deletedMessageIds.add(deletedMessageIdsItem);
    return this;
  }

  /**
   * delete of message data
   * @return deletedMessageIds
  **/
  @ApiModelProperty(required = true, value = "delete of message data")
  @NotNull

  @Valid

  public List<BigDecimal> getDeletedMessageIds() {
    return deletedMessageIds;
  }

  public void setDeletedMessageIds(List<BigDecimal> deletedMessageIds) {
    this.deletedMessageIds = deletedMessageIds;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MessageGetResponse messageGetResponse = (MessageGetResponse) o;
    return Objects.equals(this.synchStatus, messageGetResponse.synchStatus) &&
        Objects.equals(this.lastUpdateTime, messageGetResponse.lastUpdateTime) &&
        Objects.equals(this.updateRecordsNumber, messageGetResponse.updateRecordsNumber) &&
        Objects.equals(this.messageInfos, messageGetResponse.messageInfos) &&
        Objects.equals(this.deletedMessageIds, messageGetResponse.deletedMessageIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(synchStatus, lastUpdateTime, updateRecordsNumber, messageInfos, deletedMessageIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MessageGetResponse {\n");
    
    sb.append("    synchStatus: ").append(toIndentedString(synchStatus)).append("\n");
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    updateRecordsNumber: ").append(toIndentedString(updateRecordsNumber)).append("\n");
    sb.append("    messageInfos: ").append(toIndentedString(messageInfos)).append("\n");
    sb.append("    deletedMessageIds: ").append(toIndentedString(deletedMessageIds)).append("\n");
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

