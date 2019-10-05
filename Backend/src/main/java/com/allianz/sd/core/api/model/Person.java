package com.allianz.sd.core.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * Person
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-18T08:26:36.892Z")

public class Person   {
  @JsonProperty("customerID")
  private BigDecimal customerID = null;

  @JsonProperty("firstName")
  private String firstName = null;

  @JsonProperty("middleName")
  private String middleName = null;

  @JsonProperty("lastName")
  private String lastName = null;

  @JsonProperty("alternateName")
  private String alternateName = null;

  @JsonProperty("marritalStatus")
  private String marritalStatus = null;

  @JsonProperty("occupation")
  private String occupation = null;

  @JsonProperty("employer")
  private String employer = null;

  @JsonProperty("birthDate")
  private String birthDate = null;

  @JsonProperty("ageRange")
  private String ageRange = null;

  @JsonProperty("gender")
  private String gender = null;

  @JsonProperty("numberOfChildren")
  private BigDecimal numberOfChildren = null;

  @JsonProperty("addresses")
  @Valid
  private List<Address> addresses = null;

  @JsonProperty("phoneChannels")
  @Valid
  private List<PhoneChannel> phoneChannels = null;

  @JsonProperty("emailContacts")
  @Valid
  private List<com.allianz.sd.core.api.model.Email> emailContacts = null;

  @JsonProperty("annualIncomeRange")
  private String annualIncomeRange = null;

  @JsonProperty("contactLink")
  private String contactLink = null;

  @JsonProperty("familiarity")
  private String familiarity = null;

  @JsonProperty("touchPointFrequency")
  private String touchPointFrequency = null;

  @JsonProperty("leadProbability")
  private String leadProbability = null;

  @JsonProperty("isFollowed")
  private Boolean isFollowed = null;

  @JsonProperty("isOverTimeAlert")
  private Boolean isOverTimeAlert = null;

  @JsonProperty("isChangeable")
  private Boolean isChangeable = null;

  @JsonProperty("profileCompletion")
  private BigDecimal profileCompletion = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  @JsonProperty("synchDetail")
  private SynchDetail synchDetail = null;

  public Person customerID(BigDecimal customerID) {
    this.customerID = customerID;
    return this;
  }

  /**
   * id of the customer, can be null, when null new customer is created in the backend.
   * @return customerID
   **/
  @ApiModelProperty(example = "123.0", required = true, value = "id of the customer, can be null, when null new customer is created in the backend.")
  @NotNull

  @Valid

  public BigDecimal getCustomerID() {
    return customerID;
  }

  public void setCustomerID(BigDecimal customerID) {
    this.customerID = customerID;
  }

  public Person firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  /**
   * firstName of the customer
   * @return firstName
   **/
  @ApiModelProperty(example = "Tim", value = "firstName of the customer")


  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Person middleName(String middleName) {
    this.middleName = middleName;
    return this;
  }

  /**
   * middle name of the customer
   * @return middleName
   **/
  @ApiModelProperty(example = "Tim", value = "middle name of the customer")


  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public Person lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  /**
   * lastName of the customer
   * @return lastName
   **/
  @ApiModelProperty(example = "Saier", value = "lastName of the customer")


  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Person alternateName(String alternateName) {
    this.alternateName = alternateName;
    return this;
  }

  /**
   * nickname of the customer
   * @return alternateName
   **/
  @ApiModelProperty(example = "Micky Mouse", value = "nickname of the customer")


  public String getAlternateName() {
    return alternateName;
  }

  public void setAlternateName(String alternateName) {
    this.alternateName = alternateName;
  }

  public Person marritalStatus(String marritalStatus) {
    this.marritalStatus = marritalStatus;
    return this;
  }

  /**
   * marritalState of the customer
   * @return marritalStatus
   **/
  @ApiModelProperty(example = "MARRIED", value = "marritalState of the customer")


  public String getMarritalStatus() {
    return marritalStatus;
  }

  public void setMarritalStatus(String marritalStatus) {
    this.marritalStatus = marritalStatus;
  }

  public Person occupation(String occupation) {
    this.occupation = occupation;
    return this;
  }

  /**
   * occupation of the customer
   * @return occupation
   **/
  @ApiModelProperty(example = "Accountant", value = "occupation of the customer")


  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public Person employer(String employer) {
    this.employer = employer;
    return this;
  }

  /**
   * company of the customer
   * @return employer
   **/
  @ApiModelProperty(example = "NEUX", value = "company of the customer")


  public String getEmployer() {
    return employer;
  }

  public void setEmployer(String employer) {
    this.employer = employer;
  }

  public Person birthDate(String birthDate) {
    this.birthDate = birthDate;
    return this;
  }

  /**
   * The birth date of customer. Format yyyy-mm-dd
   * @return birthDate
   **/
  @ApiModelProperty(example = "1990-01-26", value = "The birth date of customer. Format yyyy-mm-dd")


  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public Person ageRange(String ageRange) {
    this.ageRange = ageRange;
    return this;
  }

  /**
   * age range of the customer
   * @return ageRange
   **/
  @ApiModelProperty(example = "1", value = "age range of the customer")


  public String getAgeRange() {
    return ageRange;
  }

  public void setAgeRange(String ageRange) {
    this.ageRange = ageRange;
  }

  public Person gender(String gender) {
    this.gender = gender;
    return this;
  }

  /**
   * gender of the customer
   * @return gender
   **/
  @ApiModelProperty(example = "FEMALE", value = "gender of the customer")


  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Person numberOfChildren(BigDecimal numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
    return this;
  }

  /**
   * amount of childer per customer. only number between 0 to 20 is allowed. 0=no children 20=children :-)
   * @return numberOfChildren
   **/
  @ApiModelProperty(example = "10.0", value = "amount of childer per customer. only number between 0 to 20 is allowed. 0=no children 20=children :-)")

  @Valid

  public BigDecimal getNumberOfChildren() {
    return numberOfChildren;
  }

  public void setNumberOfChildren(BigDecimal numberOfChildren) {
    this.numberOfChildren = numberOfChildren;
  }

  public Person addresses(List<Address> addresses) {
    this.addresses = addresses;
    return this;
  }

  public Person addAddressesItem(Address addressesItem) {
    if (this.addresses == null) {
      this.addresses = new ArrayList<Address>();
    }
    this.addresses.add(addressesItem);
    return this;
  }

  /**
   * Get addresses
   * @return addresses
   **/
  @ApiModelProperty(value = "")

  @Valid

  public List<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

  public Person phoneChannels(List<PhoneChannel> phoneChannels) {
    this.phoneChannels = phoneChannels;
    return this;
  }

  public Person addPhoneChannelsItem(PhoneChannel phoneChannelsItem) {
    if (this.phoneChannels == null) {
      this.phoneChannels = new ArrayList<PhoneChannel>();
    }
    this.phoneChannels.add(phoneChannelsItem);
    return this;
  }

  /**
   * Get phoneChannels
   * @return phoneChannels
   **/
  @ApiModelProperty(value = "")

  @Valid

  public List<PhoneChannel> getPhoneChannels() {
    return phoneChannels;
  }

  public void setPhoneChannels(List<PhoneChannel> phoneChannels) {
    this.phoneChannels = phoneChannels;
  }

  public Person emailContacts(List<com.allianz.sd.core.api.model.Email> emailContacts) {
    this.emailContacts = emailContacts;
    return this;
  }

  public Person addEmailContactsItem(com.allianz.sd.core.api.model.Email emailContactsItem) {
    if (this.emailContacts == null) {
      this.emailContacts = new ArrayList<com.allianz.sd.core.api.model.Email>();
    }
    this.emailContacts.add(emailContactsItem);
    return this;
  }

  /**
   * Get emailContacts
   * @return emailContacts
   **/
  @ApiModelProperty(value = "")

  @Valid

  public List<com.allianz.sd.core.api.model.Email> getEmailContacts() {
    return emailContacts;
  }

  public void setEmailContacts(List<com.allianz.sd.core.api.model.Email> emailContacts) {
    this.emailContacts = emailContacts;
  }

  public Person annualIncomeRange(String annualIncomeRange) {
    this.annualIncomeRange = annualIncomeRange;
    return this;
  }

  /**
   * defined range of income. Provided via datalist.
   * @return annualIncomeRange
   **/
  @ApiModelProperty(example = "$50000 - $75000", value = "defined range of income. Provided via datalist.")


  public String getAnnualIncomeRange() {
    return annualIncomeRange;
  }

  public void setAnnualIncomeRange(String annualIncomeRange) {
    this.annualIncomeRange = annualIncomeRange;
  }

  public Person contactLink(String contactLink) {
    this.contactLink = contactLink;
    return this;
  }

  /**
   * Describes how the agents knowns the person. In UI, source of the customer
   * @return contactLink
   **/
  @ApiModelProperty(example = "1", value = "Describes how the agents knowns the person. In UI, source of the customer")


  public String getContactLink() {
    return contactLink;
  }

  public void setContactLink(String contactLink) {
    this.contactLink = contactLink;
  }

  public Person familiarity(String familiarity) {
    this.familiarity = familiarity;
    return this;
  }

  /**
   * familiarity CLOSE, KNOWN, NODDING .. of the person. Is a datalist.
   * @return familiarity
   **/
  @ApiModelProperty(example = "CLOSE", value = "familiarity CLOSE, KNOWN, NODDING .. of the person. Is a datalist.")


  public String getFamiliarity() {
    return familiarity;
  }

  public void setFamiliarity(String familiarity) {
    this.familiarity = familiarity;
  }

  public Person touchPointFrequency(String touchPointFrequency) {
    this.touchPointFrequency = touchPointFrequency;
    return this;
  }

  /**
   * contact frequency of the customer per year. eg. MORE_THAN_5, 3_TO_5, 1_TO_2. Is a datalist
   * @return touchPointFrequency
   **/
  @ApiModelProperty(example = "MORE_THAN_5", value = "contact frequency of the customer per year. eg. MORE_THAN_5, 3_TO_5, 1_TO_2. Is a datalist")


  public String getTouchPointFrequency() {
    return touchPointFrequency;
  }

  public void setTouchPointFrequency(String touchPointFrequency) {
    this.touchPointFrequency = touchPointFrequency;
  }

  public Person leadProbability(String leadProbability) {
    this.leadProbability = leadProbability;
    return this;
  }

  /**
   * close a lead potential with the customer. e.g. LOW, MEDIATE, HIGH.
   * @return leadProbability
   **/
  @ApiModelProperty(example = "HIGH", value = "close a lead potential with the customer. e.g. LOW, MEDIATE, HIGH.")


  public String getLeadProbability() {
    return leadProbability;
  }

  public void setLeadProbability(String leadProbability) {
    this.leadProbability = leadProbability;
  }

  public Person isFollowed(Boolean isFollowed) {
    this.isFollowed = isFollowed;
    return this;
  }

  /**
   * is follow of the customer. true is followed.
   * @return isFollowed
   **/
  @ApiModelProperty(example = "true", value = "is follow of the customer. true is followed.")


  public Boolean isIsFollowed() {
    return isFollowed;
  }

  public void setIsFollowed(Boolean isFollowed) {
    this.isFollowed = isFollowed;
  }

  public Person isOverTimeAlert(Boolean isOverTimeAlert) {
    this.isOverTimeAlert = isOverTimeAlert;
    return this;
  }

  /**
   * Signals that the data of customer has to be removed or maintain of the customer. This is for legal purpose
   * @return isOverTimeAlert
   **/
  @ApiModelProperty(example = "true", value = "Signals that the data of customer has to be removed or maintain of the customer. This is for legal purpose")


  public Boolean isIsOverTimeAlert() {
    return isOverTimeAlert;
  }

  public void setIsOverTimeAlert(Boolean isOverTimeAlert) {
    this.isOverTimeAlert = isOverTimeAlert;
  }

  public Person isChangeable(Boolean isChangeable) {
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

  public Person profileCompletion(BigDecimal profileCompletion) {
    this.profileCompletion = profileCompletion;
    return this;
  }

  /**
   * completeness of the customers profile. Only a number between 0.0 1.0 allowed. 1=fully completed, 0.0=not started yet.
   * @return profileCompletion
   **/
  @ApiModelProperty(example = "0.55", required = true, value = "completeness of the customers profile. Only a number between 0.0 1.0 allowed. 1=fully completed, 0.0=not started yet.")
  @NotNull

  @Valid

  public BigDecimal getProfileCompletion() {
    return profileCompletion;
  }

  public void setProfileCompletion(BigDecimal profileCompletion) {
    this.profileCompletion = profileCompletion;
  }

  public Person extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Person addExtensionsItem(Extension extensionsItem) {
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

  public Person synchDetail(SynchDetail synchDetail) {
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(this.customerID, person.customerID) &&
            Objects.equals(this.firstName, person.firstName) &&
            Objects.equals(this.middleName, person.middleName) &&
            Objects.equals(this.lastName, person.lastName) &&
            Objects.equals(this.alternateName, person.alternateName) &&
            Objects.equals(this.marritalStatus, person.marritalStatus) &&
            Objects.equals(this.occupation, person.occupation) &&
            Objects.equals(this.employer, person.employer) &&
            Objects.equals(this.birthDate, person.birthDate) &&
            Objects.equals(this.ageRange, person.ageRange) &&
            Objects.equals(this.gender, person.gender) &&
            Objects.equals(this.numberOfChildren, person.numberOfChildren) &&
            Objects.equals(this.addresses, person.addresses) &&
            Objects.equals(this.phoneChannels, person.phoneChannels) &&
            Objects.equals(this.emailContacts, person.emailContacts) &&
            Objects.equals(this.annualIncomeRange, person.annualIncomeRange) &&
            Objects.equals(this.contactLink, person.contactLink) &&
            Objects.equals(this.familiarity, person.familiarity) &&
            Objects.equals(this.touchPointFrequency, person.touchPointFrequency) &&
            Objects.equals(this.leadProbability, person.leadProbability) &&
            Objects.equals(this.isFollowed, person.isFollowed) &&
            Objects.equals(this.isOverTimeAlert, person.isOverTimeAlert) &&
            Objects.equals(this.isChangeable, person.isChangeable) &&
            Objects.equals(this.profileCompletion, person.profileCompletion) &&
            Objects.equals(this.extensions, person.extensions) &&
            Objects.equals(this.synchDetail, person.synchDetail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(customerID, firstName, middleName, lastName, alternateName, marritalStatus, occupation, employer, birthDate, ageRange, gender, numberOfChildren, addresses, phoneChannels, emailContacts, annualIncomeRange, contactLink, familiarity, touchPointFrequency, leadProbability, isFollowed, isOverTimeAlert, isChangeable, profileCompletion, extensions, synchDetail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");

    sb.append("    customerID: ").append(toIndentedString(customerID)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    middleName: ").append(toIndentedString(middleName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    alternateName: ").append(toIndentedString(alternateName)).append("\n");
    sb.append("    marritalStatus: ").append(toIndentedString(marritalStatus)).append("\n");
    sb.append("    occupation: ").append(toIndentedString(occupation)).append("\n");
    sb.append("    employer: ").append(toIndentedString(employer)).append("\n");
    sb.append("    birthDate: ").append(toIndentedString(birthDate)).append("\n");
    sb.append("    ageRange: ").append(toIndentedString(ageRange)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    numberOfChildren: ").append(toIndentedString(numberOfChildren)).append("\n");
    sb.append("    addresses: ").append(toIndentedString(addresses)).append("\n");
    sb.append("    phoneChannels: ").append(toIndentedString(phoneChannels)).append("\n");
    sb.append("    emailContacts: ").append(toIndentedString(emailContacts)).append("\n");
    sb.append("    annualIncomeRange: ").append(toIndentedString(annualIncomeRange)).append("\n");
    sb.append("    contactLink: ").append(toIndentedString(contactLink)).append("\n");
    sb.append("    familiarity: ").append(toIndentedString(familiarity)).append("\n");
    sb.append("    touchPointFrequency: ").append(toIndentedString(touchPointFrequency)).append("\n");
    sb.append("    leadProbability: ").append(toIndentedString(leadProbability)).append("\n");
    sb.append("    isFollowed: ").append(toIndentedString(isFollowed)).append("\n");
    sb.append("    isOverTimeAlert: ").append(toIndentedString(isOverTimeAlert)).append("\n");
    sb.append("    isChangeable: ").append(toIndentedString(isChangeable)).append("\n");
    sb.append("    profileCompletion: ").append(toIndentedString(profileCompletion)).append("\n");
    sb.append("    extensions: ").append(toIndentedString(extensions)).append("\n");
    sb.append("    synchDetail: ").append(toIndentedString(synchDetail)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

