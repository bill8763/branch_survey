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
 * Extension
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class Extension   {
  @JsonProperty("id")
  private String id = null;

  @JsonProperty("datatype")
  private String datatype = null;

  @JsonProperty("value")
  private String value = null;

  public Extension id(String id) {
    this.id = id;
    return this;
  }

  /**
   * attribute identity id
   * @return id
  **/
  @ApiModelProperty(example = "1234", required = true, value = "attribute identity id")
  @NotNull


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Extension datatype(String datatype) {
    this.datatype = datatype;
    return this;
  }

  /**
   * attribute data type
   * @return datatype
  **/
  @ApiModelProperty(example = "text | number | boolean", required = true, value = "attribute data type")
  @NotNull


  public String getDatatype() {
    return datatype;
  }

  public void setDatatype(String datatype) {
    this.datatype = datatype;
  }

  public Extension value(String value) {
    this.value = value;
    return this;
  }

  /**
   * attribute value
   * @return value
  **/
  @ApiModelProperty(example = "1234", required = true, value = "attribute value")
  @NotNull


  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Extension extension = (Extension) o;
    return Objects.equals(this.id, extension.id) &&
        Objects.equals(this.datatype, extension.datatype) &&
        Objects.equals(this.value, extension.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, datatype, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Extension {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    datatype: ").append(toIndentedString(datatype)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

