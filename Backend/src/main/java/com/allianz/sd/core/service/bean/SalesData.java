package com.allianz.sd.core.service.bean;

public class SalesData {
    private long fyfc = 0;
    private long anp = 0;
    private long newBusinessCase = 0;
    private long manPower = 0;
    private long recruitment = 0;

    public long getFyfc() {
        return fyfc;
    }

    public void setFyfc(long fyfc) {
        this.fyfc = fyfc;
    }

    public long getAnp() {
        return anp;
    }

    public void setAnp(long anp) {
        this.anp = anp;
    }

    public long getNewBusinessCase() {
        return newBusinessCase;
    }

    public void setNewBusinessCase(long newBusinessCase) {
        this.newBusinessCase = newBusinessCase;
    }

    public long getManPower() {
        return manPower;
    }

    public void setManPower(long manPower) {
        this.manPower = manPower;
    }

    public long getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(long recruitment) {
        this.recruitment = recruitment;
    }

    public long getValue(TeamProgressDataType dataType) {
        if(dataType.equals(TeamProgressDataType.FYFC)) return getFyfc();
        else if(dataType.equals(TeamProgressDataType.ANP)) return getAnp();
        else if(dataType.equals(TeamProgressDataType.Manpower)) return getManPower();
        else if(dataType.equals(TeamProgressDataType.Recruitment)) return getRecruitment();
        return 0;
    }

    public String toString() {
        return "[SalesData][FYFC="+getFyfc()+",ANP="+getAnp()+",Manpower="+getManPower()+",Recruitment="+getRecruitment()+"]";
    }
}
