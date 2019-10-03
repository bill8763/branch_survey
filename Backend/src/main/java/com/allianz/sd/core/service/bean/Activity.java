package com.allianz.sd.core.service.bean;

import com.allianz.sd.core.InstanceCode;

public class Activity {
    private int numberOfFind = InstanceCode.DASHED;
    private int numberOfSchdule = InstanceCode.DASHED;
    private int numberOfMeet = InstanceCode.DASHED;
    private int numberOfSubmit = InstanceCode.DASHED;
    private int numberOfInforce = InstanceCode.DASHED;

    public int getNumberOfFind() {
        return numberOfFind;
    }

    public void setNumberOfFind(int numberOfFind) {
        this.numberOfFind = numberOfFind;
    }

    public int getNumberOfSchdule() {
        return numberOfSchdule;
    }

    public void setNumberOfSchdule(int numberOfSchdule) {
        this.numberOfSchdule = numberOfSchdule;
    }

    public int getNumberOfMeet() {
        return numberOfMeet;
    }

    public void setNumberOfMeet(int numberOfMeet) {
        this.numberOfMeet = numberOfMeet;
    }

    public int getNumberOfSubmit() {
        return numberOfSubmit;
    }

    public void setNumberOfSubmit(int numberOfSubmit) {
        this.numberOfSubmit = numberOfSubmit;
    }

    public int getNumberOfInforce() {
        return numberOfInforce;
    }

    public void setNumberOfInforce(int numberOfInforce) {
        this.numberOfInforce = numberOfInforce;
    }
}
