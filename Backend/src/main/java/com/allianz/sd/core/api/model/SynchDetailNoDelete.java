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
 * SynchDetailNoDelete
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class SynchDetailNoDelete   {
  @JsonProperty("lastUpdateDateTimeBackend")
  private String lastUpdateDateTimeBackend = null;

  public SynchDetailNoDelete lastUpdateDateTimeBackend(String lastUpdateDateTimeBackend) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SynchDetailNoDelete synchDetailNoDelete = (SynchDetailNoDelete) o;
    return Objects.equals(this.lastUpdateDateTimeBackend, synchDetailNoDelete.lastUpdateDateTimeBackend);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateDateTimeBackend);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SynchDetailNoDelete {\n");
    
    sb.append("    lastUpdateDateTimeBackend: ").append(toIndentedString(lastUpdateDateTimeBackend)).append("\n");
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

