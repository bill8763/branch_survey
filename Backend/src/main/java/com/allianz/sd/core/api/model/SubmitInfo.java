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
 * SubmitInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class SubmitInfo   {
  @JsonProperty("DataYear")
  private BigDecimal dataYear = null;

  /**
   * Submit Type
   */
  public enum SubmitTypeEnum {
    ALL("All"),
    
    PLAN("Plan");

    private String value;

    SubmitTypeEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static SubmitTypeEnum fromValue(String text) {
      for (SubmitTypeEnum b : SubmitTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("SubmitType")
  private SubmitTypeEnum submitType = null;

  @JsonProperty("ActivityGoalBase")
  private String activityGoalBase = null;

  /**
   * IsNeedApprove
   */
  public enum IsNeedApproveEnum {
    Y("Y"),
    
    N("N");

    private String value;

    IsNeedApproveEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static IsNeedApproveEnum fromValue(String text) {
      for (IsNeedApproveEnum b : IsNeedApproveEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

  @JsonProperty("IsNeedApprove")
  private IsNeedApproveEnum isNeedApprove = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public SubmitInfo dataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
    return this;
  }

  /**
   * Data Year
   * minimum: 2019
   * maximum: 9999
   * @return dataYear
  **/
  @ApiModelProperty(example = "2019.0", required = true, value = "Data Year")
  @NotNull

  @Valid
@DecimalMin("2019") @DecimalMax("9999") 
  public BigDecimal getDataYear() {
    return dataYear;
  }

  public void setDataYear(BigDecimal dataYear) {
    this.dataYear = dataYear;
  }

  public SubmitInfo submitType(SubmitTypeEnum submitType) {
    this.submitType = submitType;
    return this;
  }

  /**
   * Submit Type
   * @return submitType
  **/
  @ApiModelProperty(example = "All", required = true, value = "Submit Type")
  @NotNull


  public SubmitTypeEnum getSubmitType() {
    return submitType;
  }

  public void setSubmitType(SubmitTypeEnum submitType) {
    this.submitType = submitType;
  }

  public SubmitInfo activityGoalBase(String activityGoalBase) {
    this.activityGoalBase = activityGoalBase;
    return this;
  }

  /**
   * ActivityGoalBase
   * @return activityGoalBase
  **/
  @ApiModelProperty(example = "Day/Week/Month", value = "ActivityGoalBase")


  public String getActivityGoalBase() {
    return activityGoalBase;
  }

  public void setActivityGoalBase(String activityGoalBase) {
    this.activityGoalBase = activityGoalBase;
  }

  public SubmitInfo isNeedApprove(IsNeedApproveEnum isNeedApprove) {
    this.isNeedApprove = isNeedApprove;
    return this;
  }

  /**
   * IsNeedApprove
   * @return isNeedApprove
  **/
  @ApiModelProperty(example = "Y", required = true, value = "IsNeedApprove")
  @NotNull


  public IsNeedApproveEnum getIsNeedApprove() {
    return isNeedApprove;
  }

  public void setIsNeedApprove(IsNeedApproveEnum isNeedApprove) {
    this.isNeedApprove = isNeedApprove;
  }

  public SubmitInfo extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public SubmitInfo addExtensionsItem(Extension extensionsItem) {
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
    SubmitInfo submitInfo = (SubmitInfo) o;
    return Objects.equals(this.dataYear, submitInfo.dataYear) &&
        Objects.equals(this.submitType, submitInfo.submitType) &&
        Objects.equals(this.activityGoalBase, submitInfo.activityGoalBase) &&
        Objects.equals(this.isNeedApprove, submitInfo.isNeedApprove) &&
        Objects.equals(this.extensions, submitInfo.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dataYear, submitType, activityGoalBase, isNeedApprove, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubmitInfo {\n");
    
    sb.append("    dataYear: ").append(toIndentedString(dataYear)).append("\n");
    sb.append("    submitType: ").append(toIndentedString(submitType)).append("\n");
    sb.append("    activityGoalBase: ").append(toIndentedString(activityGoalBase)).append("\n");
    sb.append("    isNeedApprove: ").append(toIndentedString(isNeedApprove)).append("\n");
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

