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
 * ProgressGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-20T07:48:54.804Z")

public class ProgressGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("Progress")
  @Valid
  private List<Progress> progress = new ArrayList<Progress>();

  public ProgressGetResponse lastUpdateTime(String lastUpdateTime) {
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

  public ProgressGetResponse progress(List<Progress> progress) {
    this.progress = progress;
    return this;
  }

  public ProgressGetResponse addProgressItem(Progress progressItem) {
    this.progress.add(progressItem);
    return this;
  }

  /**
   * Progress pull data 
   * @return progress
  **/
  @ApiModelProperty(required = true, value = "Progress pull data ")
  @NotNull

  @Valid
@Size(max=2) 
  public List<Progress> getProgress() {
    return progress;
  }

  public void setProgress(List<Progress> progress) {
    this.progress = progress;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProgressGetResponse progressGetResponse = (ProgressGetResponse) o;
    return Objects.equals(this.lastUpdateTime, progressGetResponse.lastUpdateTime) &&
        Objects.equals(this.progress, progressGetResponse.progress);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, progress);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ProgressGetResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    progress: ").append(toIndentedString(progress)).append("\n");
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

