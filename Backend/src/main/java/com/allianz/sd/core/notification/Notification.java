package com.allianz.sd.core.notification;

import com.allianz.sd.core.jpa.model.Message;
import org.json.JSONObject;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/1/23
 * Time: 下午 4:20
 * To change this template use File | Settings | File Templates.
 */
public class Notification {

    private String agentID = null;
    private List<String> pushIDList = new ArrayList<String>();
    private Date pushTime = null;
    private boolean isImmediately = false;

    private String title = null;
    private String body = null;
    private NotificationType pushType = null; //Topic | Single
    private String topic = null;

    private JSONObject data = new JSONObject();

    //for integration message
    private Message message = null;

    private Map<String,String> languageTextMap = new HashMap<>();

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void addLanguageText(String key , String value) {
        this.languageTextMap.put(key,value);
    }

    public Map<String, String> getLanguageTextMap() {
        return languageTextMap;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public void addPushID(String pushID) {
        this.pushIDList.add(pushID);
    }

    public List<String> getPushID() {
        return this.pushIDList;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public boolean isImmediately() {
        return isImmediately;
    }

    public void setImmediately(boolean immediately) {
        isImmediately = immediately;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public NotificationType getPushType() {
        return pushType;
    }

    public void setPushType(NotificationType pushType) {
        this.pushType = pushType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String toString() {
        return "[title="+getTitle()+",body="+getBody()+",pushType="+getPushType()+",topic="+getTopic()+",data="+getData().toString()+"]";
    }

    public enum NotificationType {
        SINGLE,TOPIC;

        public String toString() {
            switch(this) {
                case SINGLE: return "Single";
                case TOPIC: return "Topic";
            }

            return null;
        }
    }

    
}
