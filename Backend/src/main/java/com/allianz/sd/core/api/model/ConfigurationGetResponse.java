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
 * ConfigurationGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T07:48:54.804Z")

public class ConfigurationGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("FirstUseAPP")
  private Boolean firstUseAPP = null;

  @JsonProperty("Configurations")
  @Valid
  private List<ModelConfiguration> configurations = new ArrayList<ModelConfiguration>();

  public ConfigurationGetResponse lastUpdateTime(String lastUpdateTime) {
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

  public ConfigurationGetResponse firstUseAPP(Boolean firstUseAPP) {
    this.firstUseAPP = firstUseAPP;
    return this;
  }

  /**
   * agent / leader is setting goal in APP
   * @return firstUseAPP
  **/
  @ApiModelProperty(required = true, value = "agent / leader is setting goal in APP")
  @NotNull


  public Boolean isFirstUseAPP() {
    return firstUseAPP;
  }

  public void setFirstUseAPP(Boolean firstUseAPP) {
    this.firstUseAPP = firstUseAPP;
  }

  public ConfigurationGetResponse configurations(List<ModelConfiguration> configurations) {
    this.configurations = configurations;
    return this;
  }

  public ConfigurationGetResponse addConfigurationsItem(ModelConfiguration configurationsItem) {
    this.configurations.add(configurationsItem);
    return this;
  }

  /**
   * Configuration pull data 
   * @return configurations
  **/
  @ApiModelProperty(required = true, value = "Configuration pull data ")
  @NotNull

  @Valid
  @Size(max=2)
  public List<ModelConfiguration> getConfigurations() {
    return configurations;
  }

  public void setConfigurations(List<ModelConfiguration> configurations) {
    this.configurations = configurations;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConfigurationGetResponse configurationGetResponse = (ConfigurationGetResponse) o;
    return Objects.equals(this.lastUpdateTime, configurationGetResponse.lastUpdateTime) &&
        Objects.equals(this.firstUseAPP, configurationGetResponse.firstUseAPP) &&
        Objects.equals(this.configurations, configurationGetResponse.configurations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, firstUseAPP, configurations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfigurationGetResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    firstUseAPP: ").append(toIndentedString(firstUseAPP)).append("\n");
    sb.append("    configurations: ").append(toIndentedString(configurations)).append("\n");
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

