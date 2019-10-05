package com.allianz.sd.core.goalsetting.bean;

import com.allianz.sd.core.jpa.model.AgentYearData;
import com.allianz.sd.core.service.bean.Goal;
import com.allianz.sd.core.service.bean.GoalSettingStatus;


public class GoalStatusSubject {

    private AgentYearData agentYearData = null;
    private GoalSettingStatus beforeStatus = null;
    private GoalSettingStatus afterStatus = null;
    private Goal beforeGoal = null;
    private Goal afterGoal = null;
    private Goal approveGoal = null;

    public AgentYearData getAgentYearData() {
        return agentYearData;
    }

    public void setAgentYearData(AgentYearData agentYearData) {
        this.agentYearData = agentYearData;
    }

    public GoalSettingStatus getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(GoalSettingStatus beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public GoalSettingStatus getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(GoalSettingStatus afterStatus) {
        this.afterStatus = afterStatus;
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

    public Goal getApproveGoal() {
        return approveGoal;
    }

    public void setApproveGoal(Goal approveGoal) {
        this.approveGoal = approveGoal;
    }
}
