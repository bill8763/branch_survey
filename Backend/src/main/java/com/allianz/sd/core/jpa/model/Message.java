package com.allianz.sd.core.jpa.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 5:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="TW_LH_SD_Message")
public class Message extends CreateUpdateInfo{

    @Column(name = "OrganizationalUnit")
    private String organizationalUnit = null;

    @Id
    @Column(name = "MessageID")
    @GeneratedValue(generator = "messageIDSequence")
    @SequenceGenerator(name = "messageIDSequence", sequenceName = "TW_LH_SD_Sequence_Message", allocationSize = 1)
    private Integer messageID = null;

    @Column(name = "MessageCategory")
    private String messageCategory = null;

    @Column(name = "MessageType")
    private String messageType = null;

    @Column(name = "Title")
    private String title = null;

    @Column(name = "Description")
    private String description = null;

    @Column(name = "Arguments")
    private String arguments = null;

    @Column(name = "Status")
    private String status = null;

    @Temporal(TemporalType.DATE)
    @Column(name = "MessageTime")
    private Date messageTime = null;

    @Column(name = "AgentID")
    private String agentID = null;

    @Column(name = "DataTime")
    private Date dataTime = null;

    @Column(name = "IsPopup")
    private String isPopup = null;

    @Column(name = "IsShow")
    private String isShow = null;

    @Column(name = "IsClick")
    private String isClick = null;
    
    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Integer getMessageID() {
        return messageID;
    }

    public void setMessageID(Integer messageID) {
        this.messageID = messageID;
    }

    public String getMessageCategory() {
        return messageCategory;
    }

    public void setMessageCategory(String messageCategory) {
        this.messageCategory = messageCategory;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Date messageTime) {
        this.messageTime = messageTime;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getIsPopup() {
		return isPopup;
	}

	public void setIsPopup(String isPopup) {
		this.isPopup = isPopup;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

    public String getIsClick() {
        return isClick;
    }

    public void setIsClick(String isClick) {
        this.isClick = isClick;
    }

    public String toString() {
        return "[MessageCategory = "+this.messageCategory+",MessageType = "+this.messageType+",agentID=["+agentID+"],title = "+this.title+" ,arguments =  "+this.arguments+"]";
    }
}
