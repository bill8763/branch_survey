package com.allianz.sd.core.authorization.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 4:27
 * To change this template use File | Settings | File Templates.
 */
public class LoginUser {
    private Integer agentID;
    private String agentName;
    private String appUserMode;
    private Integer goalSigningSupervisor;

    public Integer getAgentID() {
        return agentID;
    }

    public void setAgentID(Integer agentID) {
        this.agentID = agentID;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAppUserMode() {
        return appUserMode;
    }

    public void setAppUserMode(String appUserMode) {
        this.appUserMode = appUserMode;
    }

    public Integer getGoalSigningSupervisor() {
        return goalSigningSupervisor;
    }

    public void setGoalSigningSupervisor(Integer goalSigningSupervisor) {
        this.goalSigningSupervisor = goalSigningSupervisor;
    }
}
