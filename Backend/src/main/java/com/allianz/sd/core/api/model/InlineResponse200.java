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
 * InlineResponse200
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class InlineResponse200   {
  @JsonProperty("datalistId")
  @Valid
  private List<String> datalistId = null;

  public InlineResponse200 datalistId(List<String> datalistId) {
    this.datalistId = datalistId;
    return this;
  }

  public InlineResponse200 addDatalistIdItem(String datalistIdItem) {
    if (this.datalistId == null) {
      this.datalistId = new ArrayList<String>();
    }
    this.datalistId.add(datalistIdItem);
    return this;
  }

  /**
   * The name of Data List
   * @return datalistId
  **/
  @ApiModelProperty(example = "\"identityType\"", value = "The name of Data List")


  public List<String> getDatalistId() {
    return datalistId;
  }

  public void setDatalistId(List<String> datalistId) {
    this.datalistId = datalistId;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(this.datalistId, inlineResponse200.datalistId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(datalistId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    datalistId: ").append(toIndentedString(datalistId)).append("\n");
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

