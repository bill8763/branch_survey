package com.allianz.sd.core.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * PersonalProgressValue
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-02T07:45:35.739Z")

public class PersonalProgressValue   {
  /**
   * DataType of value
   */
  public enum DataTypeEnum {
    ACTUAL("Actual"),
    
    GOAL("Goal");

    private String value;

    DataTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static DataTypeEnum fromValue(String text) {
      for (DataTypeEnum b : DataTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("DataType")
  private DataTypeEnum dataType = null;

  /**
   * TimeBase of value
   */
  public enum TimeBaseEnum {
    DAY("Day"),
    
    WEEK("Week"),
    
    MONTH("Month"),
    
    QUARTER("Quarter"),
    
    YEAR("Year");

    private String value;

    TimeBaseEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TimeBaseEnum fromValue(String text) {
      for (TimeBaseEnum b : TimeBaseEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("TimeBase")
  private TimeBaseEnum timeBase = null;

  @JsonProperty("Find")
  private BigDecimal find = null;

  @JsonProperty("Schedule")
  private BigDecimal schedule = null;

  @JsonProperty("Meet")
  private BigDecimal meet = null;

  @JsonProperty("Submit")
  private BigDecimal submit = null;

  @JsonProperty("Inforce")
  private BigDecimal inforce = null;

  @JsonProperty("FYFC")
  private BigDecimal FYFC = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public PersonalProgressValue dataType(DataTypeEnum dataType) {
    this.dataType = dataType;
    return this;
  }

  /**
   * DataType of value
   * @return dataType
  **/
  @ApiModelProperty(example = "Actual", required = true, value = "DataType of value")
  @NotNull


  public DataTypeEnum getDataType() {
    return dataType;
  }

  public void setDataType(DataTypeEnum dataType) {
    this.dataType = dataType;
  }

  public PersonalProgressValue timeBase(TimeBaseEnum timeBase) {
    this.timeBase = timeBase;
    return this;
  }

  /**
   * TimeBase of value
   * @return timeBase
  **/
  @ApiModelProperty(example = "Day / Week / Month / Quarter / Year", required = true, value = "TimeBase of value")
  @NotNull


  public TimeBaseEnum getTimeBase() {
    return timeBase;
  }

  public void setTimeBase(TimeBaseEnum timeBase) {
    this.timeBase = timeBase;
  }

  public PersonalProgressValue find(BigDecimal find) {
    this.find = find;
    return this;
  }

  /**
   * Find
   * @return find
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Find")
  @NotNull

  @Valid

  public BigDecimal getFind() {
    return find;
  }

  public void setFind(BigDecimal find) {
    this.find = find;
  }

  public PersonalProgressValue schedule(BigDecimal schedule) {
    this.schedule = schedule;
    return this;
  }

  /**
   * Schedule
   * @return schedule
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Schedule")
  @NotNull

  @Valid

  public BigDecimal getSchedule() {
    return schedule;
  }

  public void setSchedule(BigDecimal schedule) {
    this.schedule = schedule;
  }

  public PersonalProgressValue meet(BigDecimal meet) {
    this.meet = meet;
    return this;
  }

  /**
   * Meet
   * @return meet
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Meet")
  @NotNull

  @Valid

  public BigDecimal getMeet() {
    return meet;
  }

  public void setMeet(BigDecimal meet) {
    this.meet = meet;
  }

  public PersonalProgressValue submit(BigDecimal submit) {
    this.submit = submit;
    return this;
  }

  /**
   * Submit
   * @return submit
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Submit")
  @NotNull

  @Valid

  public BigDecimal getSubmit() {
    return submit;
  }

  public void setSubmit(BigDecimal submit) {
    this.submit = submit;
  }

  public PersonalProgressValue inforce(BigDecimal inforce) {
    this.inforce = inforce;
    return this;
  }

  /**
   * Inforce
   * @return inforce
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "Inforce")
  @NotNull

  @Valid

  public BigDecimal getInforce() {
    return inforce;
  }

  public void setInforce(BigDecimal inforce) {
    this.inforce = inforce;
  }

  public PersonalProgressValue FYFC(BigDecimal FYFC) {
    this.FYFC = FYFC;
    return this;
  }

  /**
   * FYFC
   * @return FYFC
  **/
  @ApiModelProperty(example = "100.0", required = true, value = "FYFC")
  @NotNull

  @Valid

  public BigDecimal getFYFC() {
    return FYFC;
  }

  public void setFYFC(BigDecimal FYFC) {
    this.FYFC = FYFC;
  }

  public PersonalProgressValue extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public PersonalProgressValue addExtensionsItem(Extension extensionsItem) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PersonalProgressValue personalProgressValue = (PersonalProgressValue) o;
    return Objects.equals(this.dataType, personalProgressValue.dataType) &&
        Objects.equals(this.timeBase, personalProgressValue.timeBase) &&
        Objects.equals(this.find, personalProgressValue.find) &&
        Objects.equals(this.schedule, personalProgressValue.schedule) &&
        Objects.equals(this.meet, personalProgressValue.meet) &&
        Objects.equals(this.submit, personalProgressValue.submit) &&
        Objects.equals(this.inforce, personalProgressValue.inforce) &&
        Objects.equals(this.FYFC, personalProgressValue.FYFC) &&
        Objects.equals(this.extensions, personalProgressValue.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataType, timeBase, find, schedule, meet, submit, inforce, FYFC, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PersonalProgressValue {\n");
    
    sb.append("    dataType: ").append(toIndentedString(dataType)).append("\n");
    sb.append("    timeBase: ").append(toIndentedString(timeBase)).append("\n");
    sb.append("    find: ").append(toIndentedString(find)).append("\n");
    sb.append("    schedule: ").append(toIndentedString(schedule)).append("\n");
    sb.append("    meet: ").append(toIndentedString(meet)).append("\n");
    sb.append("    submit: ").append(toIndentedString(submit)).append("\n");
    sb.append("    inforce: ").append(toIndentedString(inforce)).append("\n");
    sb.append("    FYFC: ").append(toIndentedString(FYFC)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
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

