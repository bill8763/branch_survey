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
 * SequenceIDGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class SequenceIDGetResponse   {
  @JsonProperty("ids")
  @Valid
  private List<BigDecimal> ids = new ArrayList<BigDecimal>();

  public SequenceIDGetResponse ids(List<BigDecimal> ids) {
    this.ids = ids;
    return this;
  }

  public SequenceIDGetResponse addIdsItem(BigDecimal idsItem) {
    this.ids.add(idsItem);
    return this;
  }

  /**
   * sequence id array
   * @return ids
  **/
  @ApiModelProperty(required = true, value = "sequence id array")
  @NotNull

  @Valid

  public List<BigDecimal> getIds() {
    return ids;
  }

  public void setIds(List<BigDecimal> ids) {
    this.ids = ids;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SequenceIDGetResponse sequenceIDGetResponse = (SequenceIDGetResponse) o;
    return Objects.equals(this.ids, sequenceIDGetResponse.ids);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ids);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SequenceIDGetResponse {\n");
    
    sb.append("    ids: ").append(toIndentedString(ids)).append("\n");
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

