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
 * Message
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class Message   {
  @JsonProperty("messageID")
  private BigDecimal messageID = null;

  @JsonProperty("messageCategory")
  private String messageCategory = null;

  @JsonProperty("messageType")
  private String messageType = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("arguments")
  private Object arguments = null;

  @JsonProperty("status")
  private String status = null;

  @JsonProperty("isClick")
  private Boolean isClick = null;

  @JsonProperty("isPopup")
  private Boolean isPopup = null;

  @JsonProperty("isShow")
  private Boolean isShow = null;

  @JsonProperty("linkStatus")
  private String linkStatus = null;

  @JsonProperty("messageTime")
  private String messageTime = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  @JsonProperty("synchDetail")
  private SynchDetail synchDetail = null;

  public Message messageID(BigDecimal messageID) {
    this.messageID = messageID;
    return this;
  }

  /**
   * id of the message
   * @return messageID
  **/
  @ApiModelProperty(example = "123.0", required = true, value = "id of the message")
  @NotNull

  @Valid

  public BigDecimal getMessageID() {
    return messageID;
  }

  public void setMessageID(BigDecimal messageID) {
    this.messageID = messageID;
  }

  public Message messageCategory(String messageCategory) {
    this.messageCategory = messageCategory;
    return this;
  }

  /**
   * category of the message
   * @return messageCategory
  **/
  @ApiModelProperty(example = "Customer", required = true, value = "category of the message")
  @NotNull


  public String getMessageCategory() {
    return messageCategory;
  }

  public void setMessageCategory(String messageCategory) {
    this.messageCategory = messageCategory;
  }

  public Message messageType(String messageType) {
    this.messageType = messageType;
    return this;
  }

  /**
   * type of the customer
   * @return messageType
  **/
  @ApiModelProperty(example = "OverTimeCustomerAlert", required = true, value = "type of the customer")
  @NotNull


  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public Message title(String title) {
    this.title = title;
    return this;
  }

  /**
   * title of the message
   * @return title
  **/
  @ApiModelProperty(example = "Your Customer is delete!!", required = true, value = "title of the message")
  @NotNull


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Message description(String description) {
    this.description = description;
    return this;
  }

  /**
   * description of the message
   * @return description
  **/
  @ApiModelProperty(example = "Your Customer is delete!!", value = "description of the message")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Message arguments(Object arguments) {
    this.arguments = arguments;
    return this;
  }

  /**
   * arguments of the message
   * @return arguments
  **/
  @ApiModelProperty(value = "arguments of the message")


  public Object getArguments() {
    return arguments;
  }

  public void setArguments(Object arguments) {
    this.arguments = arguments;
  }

  public Message status(String status) {
    this.status = status;
    return this;
  }

  /**
   * status of the message
   * @return status
  **/
  @ApiModelProperty(example = "Reading | UnRead", required = true, value = "status of the message")
  @NotNull


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Message isClick(Boolean isClick) {
    this.isClick = isClick;
    return this;
  }

  /**
   * IsClick
   * @return isClick
  **/
  @ApiModelProperty(example = "true", required = true, value = "IsClick")
  @NotNull


  public Boolean isIsClick() {
    return isClick;
  }

  public void setIsClick(Boolean isClick) {
    this.isClick = isClick;
  }

  public Message isPopup(Boolean isPopup) {
    this.isPopup = isPopup;
    return this;
  }

  /**
   * IsPopup
   * @return isPopup
  **/
  @ApiModelProperty(example = "true", required = true, value = "IsPopup")
  @NotNull


  public Boolean isIsPopup() {
    return isPopup;
  }

  public void setIsPopup(Boolean isPopup) {
    this.isPopup = isPopup;
  }

  public Message isShow(Boolean isShow) {
    this.isShow = isShow;
    return this;
  }

  /**
   * Is show the message on home page
   * @return isShow
  **/
  @ApiModelProperty(example = "true", required = true, value = "Is show the message on home page")
  @NotNull


  public Boolean isIsShow() {
    return isShow;
  }

  public void setIsShow(Boolean isShow) {
    this.isShow = isShow;
  }

  public Message linkStatus(String linkStatus) {
    this.linkStatus = linkStatus;
    return this;
  }

  /**
   * Link's status of the message
   * @return linkStatus
  **/
  @ApiModelProperty(example = "DONE", required = true, value = "Link's status of the message")
  @NotNull


  public String getLinkStatus() {
    return linkStatus;
  }

  public void setLinkStatus(String linkStatus) {
    this.linkStatus = linkStatus;
  }

  public Message messageTime(String messageTime) {
    this.messageTime = messageTime;
    return this;
  }

  /**
   * starttime of the appointment event.
   * @return messageTime
  **/
  @ApiModelProperty(example = "2019-02-27T00:10:00+0800", required = true, value = "starttime of the appointment event.")
  @NotNull


  public String getMessageTime() {
    return messageTime;
  }

  public void setMessageTime(String messageTime) {
    this.messageTime = messageTime;
  }

  public Message extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Message addExtensionsItem(Extension extensionsItem) {
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

  public Message synchDetail(SynchDetail synchDetail) {
    this.synchDetail = synchDetail;
    return this;
  }

  /**
   * Get synchDetail
   * @return synchDetail
  **/
  @ApiModelProperty(value = "")

  @Valid

  public SynchDetail getSynchDetail() {
    return synchDetail;
  }

  public void setSynchDetail(SynchDetail synchDetail) {
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
    Message message = (Message) o;
    return Objects.equals(this.messageID, message.messageID) &&
        Objects.equals(this.messageCategory, message.messageCategory) &&
        Objects.equals(this.messageType, message.messageType) &&
        Objects.equals(this.title, message.title) &&
        Objects.equals(this.description, message.description) &&
        Objects.equals(this.arguments, message.arguments) &&
        Objects.equals(this.status, message.status) &&
        Objects.equals(this.isClick, message.isClick) &&
        Objects.equals(this.isPopup, message.isPopup) &&
        Objects.equals(this.isShow, message.isShow) &&
        Objects.equals(this.linkStatus, message.linkStatus) &&
        Objects.equals(this.messageTime, message.messageTime) &&
        Objects.equals(this.extensions, message.extensions) &&
        Objects.equals(this.synchDetail, message.synchDetail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messageID, messageCategory, messageType, title, description, arguments, status, isClick, isPopup, isShow, linkStatus, messageTime, extensions, synchDetail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Message {\n");
    
    sb.append("    messageID: ").append(toIndentedString(messageID)).append("\n");
    sb.append("    messageCategory: ").append(toIndentedString(messageCategory)).append("\n");
    sb.append("    messageType: ").append(toIndentedString(messageType)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    arguments: ").append(toIndentedString(arguments)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    isClick: ").append(toIndentedString(isClick)).append("\n");
    sb.append("    isPopup: ").append(toIndentedString(isPopup)).append("\n");
    sb.append("    isShow: ").append(toIndentedString(isShow)).append("\n");
    sb.append("    linkStatus: ").append(toIndentedString(linkStatus)).append("\n");
    sb.append("    messageTime: ").append(toIndentedString(messageTime)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
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

