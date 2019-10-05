package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;



/**
 * @author bill8
 *
 */
@Entity

@Table(name="TW_LH_SD_Message_Goal_Status")
public class MessageGoalStatus{

    @Id
    @Column(name = "MessageID")
    private Integer messageID = null;

    @Column(name = "GoalSettingSeq")
    private Integer goalSettingSeq = null;

    @Column(name = "Status")
    private String status = null;

	public Integer getMessageID() {
		return messageID;
	}

	public void setMessageID(Integer messageID) {
		this.messageID = messageID;
	}

	public Integer getGoalSettingSeq() {
		return goalSettingSeq;
	}

	public void setGoalSettingSeq(Integer goalSettingSeq) {
		this.goalSettingSeq = goalSettingSeq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
