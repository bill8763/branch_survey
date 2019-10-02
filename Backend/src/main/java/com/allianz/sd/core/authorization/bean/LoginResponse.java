package com.allianz.sd.core.authorization.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/8
 * Time: 下午 6:23
 * To change this template use File | Settings | File Templates.
 */
public class LoginResponse {
    private boolean isSuccess = false;
    private String agentID = null;
    private String errorCode = null;
    private String errorMsg = null;
    private String token = null;

    private Map<String,String> extensionInfo = new HashMap<String,String>();

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addExtensionInfo(String key , String val) {
        this.extensionInfo.put(key,val);
    }

    public Map<String, String> getExtensionInfo() {
        return extensionInfo;
    }
}
