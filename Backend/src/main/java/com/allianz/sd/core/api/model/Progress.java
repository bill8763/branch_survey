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
 * Progress
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class Progress   {
  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  @JsonProperty("YesterdayPoint")
  private BigDecimal yesterdayPoint = null;

  @JsonProperty("Personal")
  private PersonalProgress personal = null;

  @JsonProperty("Team")
  private TeamProgress team = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public Progress dataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
    return this;
  }

  /**
   * year of data
   * minimum: 2019
   * maximum: 9999
   * @return dataYear
  **/
  @ApiModelProperty(example = "2019.0", required = true, value = "year of data")
  @NotNull

  @Valid
@DecimalMin("2019") @DecimalMax("9999") 
  public BigDecimal getDataYear() {
    return dataYear;
  }

  public void setDataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
  }

  public Progress yesterdayPoint(BigDecimal yesterdayPoint) {
    this.yesterdayPoint = yesterdayPoint;
    return this;
  }

  /**
   * Yesterday of point
   * minimum: 0
   * @return yesterdayPoint
  **/
  @ApiModelProperty(example = "20.0", required = true, value = "Yesterday of point")
  @NotNull

  @Valid
@DecimalMin("0")
  public BigDecimal getYesterdayPoint() {
    return yesterdayPoint;
  }

  public void setYesterdayPoint(BigDecimal yesterdayPoint) {
    this.yesterdayPoint = yesterdayPoint;
  }

  public Progress personal(PersonalProgress personal) {
    this.personal = personal;
    return this;
  }

  /**
   * Get personal
   * @return personal
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public PersonalProgress getPersonal() {
    return personal;
  }

  public void setPersonal(PersonalProgress personal) {
    this.personal = personal;
  }

  public Progress team(TeamProgress team) {
    this.team = team;
    return this;
  }

  /**
   * Get team
   * @return team
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public TeamProgress getTeam() {
    return team;
  }

  public void setTeam(TeamProgress team) {
    this.team = team;
  }

  public Progress extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Progress addExtensionsItem(Extension extensionsItem) {
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
    Progress progress = (Progress) o;
    return Objects.equals(this.dataYear, progress.dataYear) &&
        Objects.equals(this.yesterdayPoint, progress.yesterdayPoint) &&
        Objects.equals(this.personal, progress.personal) &&
        Objects.equals(this.team, progress.team) &&
        Objects.equals(this.extensions, progress.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataYear, yesterdayPoint, personal, team, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Progress {\n");
    
    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    yesterdayPoint: ").append(toIndentedString(yesterdayPoint)).append("\n");
    sb.append("    personal: ").append(toIndentedString(personal)).append("\n");
    sb.append("    team: ").append(toIndentedString(team)).append("\n");
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

