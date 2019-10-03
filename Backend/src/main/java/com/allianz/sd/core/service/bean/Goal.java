package com.allianz.sd.core.service.bean;

import com.allianz.sd.core.jpa.model.GoalSettingVersion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Goal {

    private Logger logger = LoggerFactory.getLogger(Goal.class);

    private GoalSettingVersion goalSettingVersion = null;
    private Map<Integer,String> fyfcPlan = new HashMap<>();
    private Map<Integer,String> anpPlan = new HashMap<>();
    private Map<String,String> goalValue = new HashMap<>();

    public GoalSettingVersion getGoalSettingVersion() {
        return goalSettingVersion;
    }

    public void setGoalSettingVersion(GoalSettingVersion goalSettingVersion) {
        this.goalSettingVersion = goalSettingVersion;
    }

    public String getGoalValue(GoalSettingValue goalSettingValue) {
        return getGoalValue(goalSettingValue.toString());
    }

    public String getGoalValue(String mappingID) {
        return goalValue.getOrDefault(mappingID,"0");
    }

    public void addGoalValue(String mappingID,String value) {
        if(StringUtils.isEmpty(value)) value = "0";
        this.goalValue.put(mappingID, value);
    }

    public void addFyfcPlan(int month , String plan) {
        this.fyfcPlan.put(month,plan);
    }

    public void addAnpPlan(int month , String plan) {
        this.anpPlan.put(month,plan);
    }

    public Map<Integer, String> getFyfcPlan() {
        return fyfcPlan;
    }

    public Map<Integer, String> getAnpPlan() {
        return anpPlan;
    }

    public long getTotalFyfcPlan() {
        return getTotalFyfcPlan(1);
    }

    public long getTotalFyfcPlan(int yearPlanStartMonth) {
        return getTotalPlan(this.fyfcPlan,yearPlanStartMonth);
    }

    public long getTotalAnpPlan() {
        return getTotalAnpPlan(1);
    }

    public long getTotalAnpPlan(int yearPlanStartMonth) {
        return getTotalPlan(this.anpPlan,yearPlanStartMonth);
    }

    private long getTotalPlan(Map<Integer,String> planMap , int yearPlanStartMonth) {
        long total = 0;

        for(Integer month : planMap.keySet()) {
            if(month >= yearPlanStartMonth) total += Integer.parseInt(planMap.get(month));
        }

        return total;
    }
}
