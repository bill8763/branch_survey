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
 * DataListAllResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class DataListAllResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("datalist")
  @Valid
  private List<DataListResponse> datalist = new ArrayList<DataListResponse>();

  public DataListAllResponse lastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
    return this;
  }

  /**
   * lastet get datatime (servertime)
   * @return lastUpdateTime
  **/
  @ApiModelProperty(example = "2019-02-27T00:10:00+0800", required = true, value = "lastet get datatime (servertime)")
  @NotNull


  public String getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(String lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }

  public DataListAllResponse datalist(List<DataListResponse> datalist) {
    this.datalist = datalist;
    return this;
  }

  public DataListAllResponse addDatalistItem(DataListResponse datalistItem) {
    this.datalist.add(datalistItem);
    return this;
  }

  /**
   * Get datalist
   * @return datalist
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<DataListResponse> getDatalist() {
    return datalist;
  }

  public void setDatalist(List<DataListResponse> datalist) {
    this.datalist = datalist;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataListAllResponse dataListAllResponse = (DataListAllResponse) o;
    return Objects.equals(this.lastUpdateTime, dataListAllResponse.lastUpdateTime) &&
        Objects.equals(this.datalist, dataListAllResponse.datalist);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, datalist);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataListAllResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    datalist: ").append(toIndentedString(datalist)).append("\n");
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

