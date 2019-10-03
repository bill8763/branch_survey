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
 * Note
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class Note   {
  @JsonProperty("noteId")
  private BigDecimal noteId = null;

  @JsonProperty("personId")
  private BigDecimal personId = null;

  @JsonProperty("text")
  private String text = null;

  @JsonProperty("origin")
  private String origin = null;

  @JsonProperty("creationDateTime")
  private String creationDateTime = null;

  @JsonProperty("extensions")
  @Valid
  private List<Extension> extensions = null;

  @JsonProperty("synchDetail")
  private SynchDetail synchDetail = null;

  public Note noteId(BigDecimal noteId) {
    this.noteId = noteId;
    return this;
  }

  /**
   * unigue note id, can be null. if null a new note will be created
   * @return noteId
  **/
  @ApiModelProperty(example = "1213.0", required = true, value = "unigue note id, can be null. if null a new note will be created")
  @NotNull

  @Valid

  public BigDecimal getNoteId() {
    return noteId;
  }

  public void setNoteId(BigDecimal noteId) {
    this.noteId = noteId;
  }

  public Note personId(BigDecimal personId) {
    this.personId = personId;
    return this;
  }

  /**
   * id of customer or prospect to who the note is related
   * @return personId
  **/
  @ApiModelProperty(example = "123.0", required = true, value = "id of customer or prospect to who the note is related")
  @NotNull

  @Valid

  public BigDecimal getPersonId() {
    return personId;
  }

  public void setPersonId(BigDecimal personId) {
    this.personId = personId;
  }

  public Note text(String text) {
    this.text = text;
    return this;
  }

  /**
   * note from creator
   * @return text
  **/
  @ApiModelProperty(example = "booking", required = true, value = "note from creator")
  @NotNull


  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public Note origin(String origin) {
    this.origin = origin;
    return this;
  }

  /**
   * note id related to  origin note
   * @return origin
  **/
  @ApiModelProperty(example = "1213433", value = "note id related to  origin note")


  public String getOrigin() {
    return origin;
  }

  public void setOrigin(String origin) {
    this.origin = origin;
  }

  public Note creationDateTime(String creationDateTime) {
    this.creationDateTime = creationDateTime;
    return this;
  }

  /**
   * datetime of note creationtime
   * @return creationDateTime
  **/
  @ApiModelProperty(example = "2018-02-27T00:00:00+0800", required = true, value = "datetime of note creationtime")
  @NotNull


  public String getCreationDateTime() {
    return creationDateTime;
  }

  public void setCreationDateTime(String creationDateTime) {
    this.creationDateTime = creationDateTime;
  }

  public Note extensions(List<Extension> extensions) {
    this.extensions = extensions;
    return this;
  }

  public Note addExtensionsItem(Extension extensionsItem) {
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

  public Note synchDetail(SynchDetail synchDetail) {
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
    Note note = (Note) o;
    return Objects.equals(this.noteId, note.noteId) &&
        Objects.equals(this.personId, note.personId) &&
        Objects.equals(this.text, note.text) &&
        Objects.equals(this.origin, note.origin) &&
        Objects.equals(this.creationDateTime, note.creationDateTime) &&
        Objects.equals(this.extensions, note.extensions) &&
        Objects.equals(this.synchDetail, note.synchDetail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(noteId, personId, text, origin, creationDateTime, extensions, synchDetail);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Note {\n");
    
    sb.append("    noteId: ").append(toIndentedString(noteId)).append("\n");
    sb.append("    personId: ").append(toIndentedString(personId)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    origin: ").append(toIndentedString(origin)).append("\n");
    sb.append("    creationDateTime: ").append(toIndentedString(creationDateTime)).append("\n");
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

