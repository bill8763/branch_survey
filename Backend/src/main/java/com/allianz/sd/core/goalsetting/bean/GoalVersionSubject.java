package com.allianz.sd.core.goalsetting.bean;

import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.service.bean.Goal;

public class GoalVersionSubject {
    public static final String CREATE = "CREATE";
    public static final String CHANGE = "CHANGE";

    private String status = null;
    private AgentYearData agentYearData = null;
    private Goal beforeGoal = null;
    private Goal afterGoal = null;

    public GoalVersionSubject(String status, AgentYearData agentYearData, Goal beforeGoal) {
        this.status = status;
        this.agentYearData = agentYearData;
        this.beforeGoal = beforeGoal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AgentYearData getAgentYearData() {
        return agentYearData;
    }

    public void setAgentYearData(AgentYearData agentYearData) {
        this.agentYearData = agentYearData;
    }

    public Goal getBeforeGoal() {
        return beforeGoal;
    }

    public void setBeforeGoal(Goal beforeGoal) {
        this.beforeGoal = beforeGoal;
    }

    public Goal getAfterGoal() {
        return afterGoal;
    }

    public void setAfterGoal(Goal afterGoal) {
        this.afterGoal = afterGoal;
    }
}
