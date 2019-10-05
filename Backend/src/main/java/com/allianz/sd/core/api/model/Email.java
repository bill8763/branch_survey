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
 * Email
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class Email   {
  @JsonProperty("emailID")
  private BigDecimal emailID = null;

  @JsonProperty("emailType")
  private String emailType = null;

  @JsonProperty("email")
  private String email = null;

  @JsonProperty("isChangeable")
  private Boolean isChangeable = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  public Email emailID(BigDecimal emailID) {
    this.emailID = emailID;
    return this;
  }

  /**
   * email id of email. Can be null. When null new Email will be created in the backend.
   * @return emailID
  **/
  @ApiModelProperty(example = "123.0", value = "email id of email. Can be null. When null new Email will be created in the backend.")

  @Valid

  public BigDecimal getEmailID() {
    return emailID;
  }

  public void setEmailID(BigDecimal emailID) {
    this.emailID = emailID;
  }

  public Email emailType(String emailType) {
    this.emailType = emailType;
    return this;
  }

  /**
   * type of email. Its a datalist.
   * @return emailType
  **/
  @ApiModelProperty(example = "PRIVATE, WORK", value = "type of email. Its a datalist.")


  public String getEmailType() {
    return emailType;
  }

  public void setEmailType(String emailType) {
    this.emailType = emailType;
  }

  public Email email(String email) {
    this.email = email;
    return this;
  }

  /**
   * mail address of email
   * @return email
  **/
  @ApiModelProperty(example = "titanteng@neux.com.tw", value = "mail address of email")


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Email isChangeable(Boolean isChangeable) {
    this.isChangeable = isChangeable;
    return this;
  }

  /**
   * When customer is from OPUS or other core systems main specific attribute cant be chagensdatasource. true can be changed.
   * @return isChangeable
  **/
  @ApiModelProperty(example = "false", required = true, value = "When customer is from OPUS or other core systems main specific attribute cant be chagensdatasource. true can be changed.")
  @NotNull


  public Boolean isIsChangeable() {
    return isChangeable;
  }

  public void setIsChangeable(Boolean isChangeable) {
    this.isChangeable = isChangeable;
  }

  public Email extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Email addExtensionsItem(Extension extensionsItem) {
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
    Email email = (Email) o;
    return Objects.equals(this.emailID, email.emailID) &&
        Objects.equals(this.emailType, email.emailType) &&
        Objects.equals(this.email, email.email) &&
        Objects.equals(this.isChangeable, email.isChangeable) &&
        Objects.equals(this.extensions, email.extensions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(emailID, emailType, email, isChangeable, extensions);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Email {\n");
    
    sb.append("    emailID: ").append(toIndentedString(emailID)).append("\n");
    sb.append("    emailType: ").append(toIndentedString(emailType)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    isChangeable: ").append(toIndentedString(isChangeable)).append("\n");
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

