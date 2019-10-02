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
 * NotesGetResponse
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-09-01T09:31:06.877Z")

public class NotesGetResponse   {
  @JsonProperty("lastUpdateTime")
  private String lastUpdateTime = null;

  @JsonProperty("notes")
  @Valid
  private List<Note> notes = new ArrayList<Note>();

  @JsonProperty("deletedNoteIds")
  @Valid
  private List<BigDecimal> deletedNoteIds = new ArrayList<BigDecimal>();

  public NotesGetResponse lastUpdateTime(String lastUpdateTime) {
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

  public NotesGetResponse notes(List<Note> notes) {
    this.notes = notes;
    return this;
  }

  public NotesGetResponse addNotesItem(Note notesItem) {
    this.notes.add(notesItem);
    return this;
  }

  /**
   * Note list after synchronization
   * @return notes
  **/
  @ApiModelProperty(required = true, value = "Note list after synchronization")
  @NotNull

  @Valid

  public List<Note> getNotes() {
    return notes;
  }

  public void setNotes(List<Note> notes) {
    this.notes = notes;
  }

  public NotesGetResponse deletedNoteIds(List<BigDecimal> deletedNoteIds) {
    this.deletedNoteIds = deletedNoteIds;
    return this;
  }

  public NotesGetResponse addDeletedNoteIdsItem(BigDecimal deletedNoteIdsItem) {
    this.deletedNoteIds.add(deletedNoteIdsItem);
    return this;
  }

  /**
   * deleted note id list
   * @return deletedNoteIds
  **/
  @ApiModelProperty(required = true, value = "deleted note id list")
  @NotNull

  @Valid

  public List<BigDecimal> getDeletedNoteIds() {
    return deletedNoteIds;
  }

  public void setDeletedNoteIds(List<BigDecimal> deletedNoteIds) {
    this.deletedNoteIds = deletedNoteIds;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NotesGetResponse notesGetResponse = (NotesGetResponse) o;
    return Objects.equals(this.lastUpdateTime, notesGetResponse.lastUpdateTime) &&
        Objects.equals(this.notes, notesGetResponse.notes) &&
        Objects.equals(this.deletedNoteIds, notesGetResponse.deletedNoteIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastUpdateTime, notes, deletedNoteIds);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class NotesGetResponse {\n");
    
    sb.append("    lastUpdateTime: ").append(toIndentedString(lastUpdateTime)).append("\n");
    sb.append("    notes: ").append(toIndentedString(notes)).append("\n");
    sb.append("    deletedNoteIds: ").append(toIndentedString(deletedNoteIds)).append("\n");
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

