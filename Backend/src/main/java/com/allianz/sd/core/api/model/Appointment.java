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
 * Appointment
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class Appointment   {
  @JsonProperty("appointmentId")
  private BigDecimal appointmentId = null;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("meetingLocation")
  private String meetingLocation = null;

  @JsonProperty("appointmentType")
  private String appointmentType = null;

  @JsonProperty("allDay")
  private Boolean allDay = null;

  @JsonProperty("startDateTime")
  private String startDateTime = null;

  @JsonProperty("endDateTime")
  private String endDateTime = null;

  @JsonProperty("alertTimes")
  @Valid
  private List<String> alertTimes = null;

  @JsonProperty("personId")
  private BigDecimal personId = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("isChangeable")
  private Boolean isChangeable = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  @JsonProperty("synchDetail")
  private SynchDetail synchDetail = null;

  public Appointment appointmentId(BigDecimal appointmentId) {
    this.appointmentId = appointmentId;
    return this;
  }

  /**
   * calendarId of the calendar event. can be null. When null the a new one created
   * @return appointmentId
  **/
  @ApiModelProperty(required = true, value = "calendarId of the calendar event. can be null. When null the a new one created")
  @NotNull

  @Valid

  public BigDecimal getAppointmentId() {
    return appointmentId;
  }

  public void setAppointmentId(BigDecimal appointmentId) {
    this.appointmentId = appointmentId;
  }

  public Appointment name(String name) {
    this.name = name;
    return this;
  }

  /**
   * the name aka. title of the appointment event
   * @return name
  **/
  @ApiModelProperty(example = "Tim", required = true, value = "the name aka. title of the appointment event")
  @NotNull


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Appointment meetingLocation(String meetingLocation) {
    this.meetingLocation = meetingLocation;
    return this;
  }

  /**
   * location of the appointment event
   * @return meetingLocation
  **/
  @ApiModelProperty(example = "Saier", value = "location of the appointment event")


  public String getMeetingLocation() {
    return meetingLocation;
  }

  public void setMeetingLocation(String meetingLocation) {
    this.meetingLocation = meetingLocation;
  }

  public Appointment appointmentType(String appointmentType) {
    this.appointmentType = appointmentType;
    return this;
  }

  /**
   * the type of the appointment event. The types are delivered as datalist
   * @return appointmentType
  **/
  @ApiModelProperty(example = "SalesAppointment", required = true, value = "the type of the appointment event. The types are delivered as datalist")
  @NotNull


  public String getAppointmentType() {
    return appointmentType;
  }

  public void setAppointmentType(String appointmentType) {
    this.appointmentType = appointmentType;
  }

  public Appointment allDay(Boolean allDay) {
    this.allDay = allDay;
    return this;
  }

  /**
   * is a all day 8-hour appointment = true
   * @return allDay
  **/
  @ApiModelProperty(example = "true", required = true, value = "is a all day 8-hour appointment = true")
  @NotNull


  public Boolean isAllDay() {
    return allDay;
  }

  public void setAllDay(Boolean allDay) {
    this.allDay = allDay;
  }

  public Appointment startDateTime(String startDateTime) {
    this.startDateTime = startDateTime;
    return this;
  }

  /**
   * starttime of the appointment event.
   * @return startDateTime
  **/
  @ApiModelProperty(example = "2019-02-27T00:10:00+0800", required = true, value = "starttime of the appointment event.")
  @NotNull


  public String getStartDateTime() {
    return startDateTime;
  }

  public void setStartDateTime(String startDateTime) {
    this.startDateTime = startDateTime;
  }

  public Appointment endDateTime(String endDateTime) {
    this.endDateTime = endDateTime;
    return this;
  }

  /**
   * endtime of the appointment event
   * @return endDateTime
  **/
  @ApiModelProperty(example = "2019-02-27T00:11:00+0800", required = true, value = "endtime of the appointment event")
  @NotNull


  public String getEndDateTime() {
    return endDateTime;
  }

  public void setEndDateTime(String endDateTime) {
    this.endDateTime = endDateTime;
  }

  public Appointment alertTimes(List<String> alertTimes) {
    this.alertTimes = alertTimes;
    return this;
  }

  public Appointment addAlertTimesItem(String alertTimesItem) {
    if (this.alertTimes == null) {
      this.alertTimes = new ArrayList<String>();
    }
    this.alertTimes.add(alertTimesItem);
    return this;
  }

  /**
   * alert 1 of the appointment event. From datalist
   * @return alertTimes
  **/
  @ApiModelProperty(example = "\"15 minutes, 1 hour\"", value = "alert 1 of the appointment event. From datalist")


  public List<String> getAlertTimes() {
    return alertTimes;
  }

  public void setAlertTimes(List<String> alertTimes) {
    this.alertTimes = alertTimes;
  }

  public Appointment personId(BigDecimal personId) {
    this.personId = personId;
    return this;
  }

  /**
   * ref id to invited person.
   * @return personId
  **/
  @ApiModelProperty(example = "1223434.0", value = "ref id to invited person.")

  @Valid

  public BigDecimal getPersonId() {
    return personId;
  }

  public void setPersonId(BigDecimal personId) {
    this.personId = personId;
  }

  public Appointment description(String description) {
    this.description = description;
    return this;
  }

  /**
   * detailed text,remark of the appointment event
   * @return description
  **/
  @ApiModelProperty(example = "We will meet up next Monday at Fancy pizza house", value = "detailed text,remark of the appointment event")


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Appointment isChangeable(Boolean isChangeable) {
    this.isChangeable = isChangeable;
    return this;
  }

  /**
   * When appointment is from OPUS or other core mail system then it cant be changed in the front-end. true indicated the is can be changed in the front-end and the change will be propagaded through attached systems.
   * @return isChangeable
  **/
  @ApiModelProperty(example = "true", value = "When appointment is from OPUS or other core mail system then it cant be changed in the front-end. true indicated the is can be changed in the front-end and the change will be propagaded through attached systems.")


  public Boolean isIsChangeable() {
    return isChangeable;
  }

  public void setIsChangeable(Boolean isChangeable) {
    this.isChangeable = isChangeable;
  }

  public Appointment extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Appointment addExtensionsItem(Extension extensionsItem) {
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

  public Appointment synchDetail(SynchDetail synchDetail) {
    this.synchDetail = synchDetail;
    return this;
  }

  /**
   * Get synchDetail
   * @return synchDetail
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public SynchDetail getSynchDetail() {
    return synchDetail;
  }

  public void setSynchDetail(SynchDetail synchDetail) {
    this.synchDetail = synchDetail;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Appointment appointment = (Appointment) o;
    return Objects.equals(this.appointmentId, appointment.appointmentId) &&
        Objects.equals(this.name, appointment.name) &&
        Objects.equals(this.meetingLocation, appointment.meetingLocation) &&
        Objects.equals(this.appointmentType, appointment.appointmentType) &&
        Objects.equals(this.allDay, appointment.allDay) &&
        Objects.equals(this.startDateTime, appointment.startDateTime) &&
        Objects.equals(this.endDateTime, appointment.endDateTime) &&
        Objects.equals(this.alertTimes, appointment.alertTimes) &&
        Objects.equals(this.personId, appointment.personId) &&
        Objects.equals(this.description, appointment.description) &&
        Objects.equals(this.isChangeable, appointment.isChangeable) &&
        Objects.equals(this.extensions, appointment.extensions) &&
        Objects.equals(this.synchDetail, appointment.synchDetail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(appointmentId, name, meetingLocation, appointmentType, allDay, startDateTime, endDateTime, alertTimes, personId, description, isChangeable, extensions, synchDetail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Appointment {\n");
    
    sb.append("    appointmentId: ").append(toIndentedString(appointmentId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    meetingLocation: ").append(toIndentedString(meetingLocation)).append("\n");
    sb.append("    appointmentType: ").append(toIndentedString(appointmentType)).append("\n");
    sb.append("    allDay: ").append(toIndentedString(allDay)).append("\n");
    sb.append("    startDateTime: ").append(toIndentedString(startDateTime)).append("\n");
    sb.append("    endDateTime: ").append(toIndentedString(endDateTime)).append("\n");
    sb.append("    alertTimes: ").append(toIndentedString(alertTimes)).append("\n");
    sb.append("    personId: ").append(toIndentedString(personId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    isChangeable: ").append(toIndentedString(isChangeable)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
    sb.append("    synchDetail: ").append(toIndentedString(synchDetail)).append("\n");
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

