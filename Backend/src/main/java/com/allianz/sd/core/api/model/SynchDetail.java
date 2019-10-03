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
 * SynchDetail
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class SynchDetail   {
  @JsonProperty("clientTime")
  private String clientTime = null;

  @JsonProperty("lastUpdateDateTimeBackend")
  private String lastUpdateDateTimeBackend = null;

  @JsonProperty("toDelete")
  private Boolean toDelete = null;

  public SynchDetail clientTime(String clientTime) {
    this.clientTime = clientTime;
    return this;
  }

  /**
   * device update time of the customer
   * @return clientTime
  **/
  @ApiModelProperty(example = "2018-02-27T00:00:00+0800", value = "device update time of the customer")


  public String getClientTime() {
    return clientTime;
  }

  public void setClientTime(String clientTime) {
    this.clientTime = clientTime;
  }

  public SynchDetail lastUpdateDateTimeBackend(String lastUpdateDateTimeBackend) {
    this.lastUpdateDateTimeBackend = lastUpdateDateTimeBackend;
    return this;
  }

  /**
   * server last update time of the customer
   * @return lastUpdateDateTimeBackend
  **/
  @ApiModelProperty(example = "2018-02-27T00:00:00+0800", required = true, value = "server last update time of the customer")
  @NotNull


  public String getLastUpdateDateTimeBackend() {
    return lastUpdateDateTimeBackend;
  }

  public void setLastUpdateDateTimeBackend(String lastUpdateDateTimeBackend) {
    this.lastUpdateDateTimeBackend = lastUpdateDateTimeBackend;
  }

  public SynchDetail toDelete(Boolean toDelete) {
    this.toDelete = toDelete;
    return this;
  }

  /**
   * is delete of the customer true=to be deleted, false=not to be deleted
   * @return toDelete
  **/
  @ApiModelProperty(example = "false", required = true, value = "is delete of the customer true=to be deleted, false=not to be deleted")
  @NotNull


  public Boolean isToDelete() {
    return toDelete;
  }

  public void setToDelete(Boolean toDelete) {
    this.toDelete = toDelete;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SynchDetail synchDetail = (SynchDetail) o;
    return Objects.equals(this.clientTime, synchDetail.clientTime) &&
        Objects.equals(this.lastUpdateDateTimeBackend, synchDetail.lastUpdateDateTimeBackend) &&
        Objects.equals(this.toDelete, synchDetail.toDelete);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientTime, lastUpdateDateTimeBackend, toDelete);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SynchDetail {\n");
    
    sb.append("    clientTime: ").append(toIndentedString(clientTime)).append("\n");
    sb.append("    lastUpdateDateTimeBackend: ").append(toIndentedString(lastUpdateDateTimeBackend)).append("\n");
    sb.append("    toDelete: ").append(toIndentedString(toDelete)).append("\n");
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

