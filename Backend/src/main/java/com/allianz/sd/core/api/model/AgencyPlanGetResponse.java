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
 * AgencyPlanGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T07:48:54.804Z")

public class AgencyPlanGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("AgencyPlans")
  @Valid
  private List<AgencyPlan> agencyPlans = new ArrayList<AgencyPlan>();

  public AgencyPlanGetResponse lastUpdateTime(String lastUpdateTime) {
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

  public AgencyPlanGetResponse agencyPlans(List<AgencyPlan> agencyPlans) {
    this.agencyPlans = agencyPlans;
    return this;
  }

  public AgencyPlanGetResponse addAgencyPlansItem(AgencyPlan agencyPlansItem) {
    this.agencyPlans.add(agencyPlansItem);
    return this;
  }

  /**
   * AgencyPlan pull data 
   * @return agencyPlans
  **/
  @ApiModelProperty(required = true, value = "AgencyPlan pull data ")
  @NotNull

  @Valid

  public List<AgencyPlan> getAgencyPlans() {
    return agencyPlans;
  }

  public void setAgencyPlans(List<AgencyPlan> agencyPlans) {
    this.agencyPlans = agencyPlans;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AgencyPlanGetResponse agencyPlanGetResponse = (AgencyPlanGetResponse) o;
    return Objects.equals(this.lastUpdateTime, agencyPlanGetResponse.lastUpdateTime) &&
        Objects.equals(this.agencyPlans, agencyPlanGetResponse.agencyPlans);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, agencyPlans);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AgencyPlanGetResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    agencyPlans: ").append(toIndentedString(agencyPlans)).append("\n");
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

