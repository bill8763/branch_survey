package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ActualGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T07:48:54.804Z")

public class ActualGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("Actual")
  @Valid
  private List<Actual> actual = new ArrayList<Actual>();

  public ActualGetResponse lastUpdateTime(String lastUpdateTime) {
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

  public ActualGetResponse actual(List<Actual> actual) {
    this.actual = actual;
    return this;
  }

  public ActualGetResponse addActualItem(Actual actualItem) {
    this.actual.add(actualItem);
    return this;
  }

  /**
   * Actual pull data 
   * @return actual
  **/
  @ApiModelProperty(required = true, value = "Actual pull data ")
  @NotNull

  @Valid
  public List<Actual> getActual() {
    return actual;
  }

  public void setActual(List<Actual> actual) {
    this.actual = actual;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ActualGetResponse actualGetResponse = (ActualGetResponse) o;
    return Objects.equals(this.lastUpdateTime, actualGetResponse.lastUpdateTime) &&
        Objects.equals(this.actual, actualGetResponse.actual);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, actual);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ActualGetResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    actual: ").append(toIndentedString(actual)).append("\n");
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

