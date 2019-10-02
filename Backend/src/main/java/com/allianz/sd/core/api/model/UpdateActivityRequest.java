package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * UpdateActivityRequest
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class UpdateActivityRequest   {
  @JsonProperty("AgentID")
  private String agentID = null;

  @JsonProperty("DataDate")
  private String dataDate = null;

  /**
   * update field of activity
   */
  public enum FieldEnum {
    FIND("Find"),
    
    SCHEDULE("Schedule"),
    
    MEET("Meet"),
    
    SUBMIT("Submit"),
    
    INFORCE("Inforce");

    private String value;

    FieldEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static FieldEnum fromValue(String text) {
      for (FieldEnum b : FieldEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("Field")
  private FieldEnum field = null;

  @JsonProperty("Value")
  private BigDecimal value = null;

  public UpdateActivityRequest agentID(String agentID) {
    this.agentID = agentID;
    return this;
  }

  /**
   * string
   * @return agentID
  **/
  @ApiModelProperty(example = "123", required = true, value = "string")
  @NotNull


  public String getAgentID() {
    return agentID;
  }

  public void setAgentID(String agentID) {
    this.agentID = agentID;
  }

  public UpdateActivityRequest dataDate(String dataDate) {
    this.dataDate = dataDate;
    return this;
  }

  /**
   * Format yyyy-mm-dd
   * @return dataDate
  **/
  @ApiModelProperty(example = "1990-01-26", required = true, value = "Format yyyy-mm-dd")
  @NotNull


  public String getDataDate() {
    return dataDate;
  }

  public void setDataDate(String dataDate) {
    this.dataDate = dataDate;
  }

  public UpdateActivityRequest field(FieldEnum field) {
    this.field = field;
    return this;
  }

  /**
   * update field of activity
   * @return field
  **/
  @ApiModelProperty(example = "Inforce", required = true, value = "update field of activity")
  @NotNull


  public FieldEnum getField() {
    return field;
  }

  public void setField(FieldEnum field) {
    this.field = field;
  }

  public UpdateActivityRequest value(BigDecimal value) {
    this.value = value;
    return this;
  }

  /**
   * update value
   * @return value
  **/
  @ApiModelProperty(example = "10.0", required = true, value = "update value")
  @NotNull

  @Valid

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
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
    UpdateActivityRequest updateActivityRequest = (UpdateActivityRequest) o;
    return Objects.equals(this.agentID, updateActivityRequest.agentID) &&
        Objects.equals(this.dataDate, updateActivityRequest.dataDate) &&
        Objects.equals(this.field, updateActivityRequest.field) &&
        Objects.equals(this.value, updateActivityRequest.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(agentID, dataDate, field, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateActivityRequest {\n");
    
    sb.append("    agentID: ").append(toIndentedString(agentID)).append("\n");
    sb.append("    dataDate: ").append(toIndentedString(dataDate)).append("\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
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

