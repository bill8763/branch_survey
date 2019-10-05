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
 * DataListResponse
 */
@ApiModel(description = "DataListResponse")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class DataListResponse   {
  @JsonProperty("datalistId")
  private String datalistId = null;

  @JsonProperty("values")
  @Valid
  private List<DataListValue> values = new ArrayList<DataListValue>();

  public DataListResponse datalistId(String datalistId) {
    this.datalistId = datalistId;
    return this;
  }

  /**
   * The Name of Data List
   * @return datalistId
  **/
  @ApiModelProperty(example = "identityType", required = true, value = "The Name of Data List")
  @NotNull


  public String getDatalistId() {
    return datalistId;
  }

  public void setDatalistId(String datalistId) {
    this.datalistId = datalistId;
  }

  public DataListResponse values(List<DataListValue> values) {
    this.values = values;
    return this;
  }

  public DataListResponse addValuesItem(DataListValue valuesItem) {
    this.values.add(valuesItem);
    return this;
  }

  /**
   * The List of values with labels (codes)
   * @return values
  **/
  @ApiModelProperty(required = true, value = "The List of values with labels (codes)")
  @NotNull

  @Valid

  public List<DataListValue> getValues() {
    return values;
  }

  public void setValues(List<DataListValue> values) {
    this.values = values;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DataListResponse dataListResponse = (DataListResponse) o;
    return Objects.equals(this.datalistId, dataListResponse.datalistId) &&
        Objects.equals(this.values, dataListResponse.values);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datalistId, values);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DataListResponse {\n");
    
    sb.append("    datalistId: ").append(toIndentedString(datalistId)).append("\n");
    sb.append("    values: ").append(toIndentedString(values)).append("\n");
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

