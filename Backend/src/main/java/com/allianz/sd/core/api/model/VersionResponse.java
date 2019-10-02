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
 * VersionResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class VersionResponse   {
  @JsonProperty("version")
  private String version = null;

  @JsonProperty("updateType")
  private String updateType = null;

  @JsonProperty("appPath")
  private String appPath = null;

  @JsonProperty("sqls")
  @Valid
  private List<String> sqls = null;

  public VersionResponse version(String version) {
    this.version = version;
    return this;
  }

  /**
   * current APP version
   * @return version
  **/
  @ApiModelProperty(example = "1.0", required = true, value = "current APP version")
  @NotNull


  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public VersionResponse updateType(String updateType) {
    this.updateType = updateType;
    return this;
  }

  /**
   * update type
   * @return updateType
  **/
  @ApiModelProperty(example = "APP | SQL", required = true, value = "update type")

  public String getUpdateType() {
    return updateType;
  }

  public void setUpdateType(String updateType) {
    this.updateType = updateType;
  }

  public VersionResponse appPath(String appPath) {
    this.appPath = appPath;
    return this;
  }

  /**
   * ipa/apk download URL
   * @return appPath
  **/
  @ApiModelProperty(example = "https://xxxx.xxxx.xxx/1.0/snd.apk | snd.ipa", required = true, value = "ipa/apk download URL")

  public String getAppPath() {
    return appPath;
  }

  public void setAppPath(String appPath) {
    this.appPath = appPath;
  }

  public VersionResponse sqls(List<String> sqls) {
    this.sqls = sqls;
    return this;
  }

  public VersionResponse addSqlsItem(String sqlsItem) {
    if (this.sqls == null) {
      this.sqls = new ArrayList<String>();
    }
    this.sqls.add(sqlsItem);
    return this;
  }

  /**
   * SQLite.sql download URL
   * @return sqls
  **/
  @ApiModelProperty(value = "SQLite.sql download URL")


  public List<String> getSqls() {
    return sqls;
  }

  public void setSqls(List<String> sqls) {
    this.sqls = sqls;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    VersionResponse versionResponse = (VersionResponse) o;
    return Objects.equals(this.version, versionResponse.version) &&
        Objects.equals(this.updateType, versionResponse.updateType) &&
        Objects.equals(this.appPath, versionResponse.appPath) &&
        Objects.equals(this.sqls, versionResponse.sqls);
  }

  @Override
  public int hashCode() {
    return Objects.hash(version, updateType, appPath, sqls);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class VersionResponse {\n");
    
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    updateType: ").append(toIndentedString(updateType)).append("\n");
    sb.append("    appPath: ").append(toIndentedString(appPath)).append("\n");
    sb.append("    sqls: ").append(toIndentedString(sqls)).append("\n");
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

