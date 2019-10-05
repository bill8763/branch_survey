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
 * AppointmentGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class AppointmentGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("appointments")
  @Valid
  private List<Appointment> appointments = new ArrayList<Appointment>();

  @JsonProperty("deletedAppointmentIds")
  @Valid
  private List<BigDecimal> deletedAppointmentIds = new ArrayList<BigDecimal>();

  public AppointmentGetResponse lastUpdateTime(String lastUpdateTime) {
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

  public AppointmentGetResponse appointments(List<Appointment> appointments) {
    this.appointments = appointments;
    return this;
  }

  public AppointmentGetResponse addAppointmentsItem(Appointment appointmentsItem) {
    this.appointments.add(appointmentsItem);
    return this;
  }

  /**
   * Appoinments list data
   * @return appointments
  **/
  @ApiModelProperty(required = true, value = "Appoinments list data")
  @NotNull

  @Valid

  public List<Appointment> getAppointments() {
    return appointments;
  }

  public void setAppointments(List<Appointment> appointments) {
    this.appointments = appointments;
  }

  public AppointmentGetResponse deletedAppointmentIds(List<BigDecimal> deletedAppointmentIds) {
    this.deletedAppointmentIds = deletedAppointmentIds;
    return this;
  }

  public AppointmentGetResponse addDeletedAppointmentIdsItem(BigDecimal deletedAppointmentIdsItem) {
    this.deletedAppointmentIds.add(deletedAppointmentIdsItem);
    return this;
  }

  /**
   * deleted appointment id list
   * @return deletedAppointmentIds
  **/
  @ApiModelProperty(required = true, value = "deleted appointment id list")
  @NotNull

  @Valid

  public List<BigDecimal> getDeletedAppointmentIds() {
    return deletedAppointmentIds;
  }

  public void setDeletedAppointmentIds(List<BigDecimal> deletedAppointmentIds) {
    this.deletedAppointmentIds = deletedAppointmentIds;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppointmentGetResponse appointmentGetResponse = (AppointmentGetResponse) o;
    return Objects.equals(this.lastUpdateTime, appointmentGetResponse.lastUpdateTime) &&
        Objects.equals(this.appointments, appointmentGetResponse.appointments) &&
        Objects.equals(this.deletedAppointmentIds, appointmentGetResponse.deletedAppointmentIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, appointments, deletedAppointmentIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppointmentGetResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    appointments: ").append(toIndentedString(appointments)).append("\n");
    sb.append("    deletedAppointmentIds: ").append(toIndentedString(deletedAppointmentIds)).append("\n");
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

