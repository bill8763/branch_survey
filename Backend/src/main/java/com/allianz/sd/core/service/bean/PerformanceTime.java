package com.allianz.sd.core.service.bean;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PerformanceTime {
    private int year = -1;
    private int quarter = -1;
    private int month = -1;
    private int days = -1;

    private int actualCalcEndMonth = -1;
    private int planClacStartMonth = -1;

    private int endMonthOfYear = 12;

    private List<Integer> monthOfQuarter = null;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getActualCalcEndMonth() {
        return actualCalcEndMonth;
    }

    public void setActualCalcEndMonth(int actualCalcEndMonth) {
        this.actualCalcEndMonth = actualCalcEndMonth;
    }

    public int getPlanClacStartMonth() {
        return planClacStartMonth;
    }

    public void setPlanClacStartMonth(int planClacStartMonth) {
        this.planClacStartMonth = planClacStartMonth;
    }

    public List<Integer> getMonthOfQuarter() {
        return monthOfQuarter;
    }

    public void setMonthOfQuarter(List<Integer> monthOfQuarter) {
        this.monthOfQuarter = monthOfQuarter;
    }

    public int getEndMonthOfYear() {
        return endMonthOfYear;
    }

    public void setEndMonthOfYear(int endMonthOfYear) {
        this.endMonthOfYear = endMonthOfYear;
    }

    public Calendar toCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month + 1);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

        return calendar;
    }

    public Date toStartDate() {

        Calendar calendar = toCalendar();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }



    public Date toEndDate() {
        Calendar calendar = toCalendar();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime();
    }

    public String toString() {

        return "[PerformanceTime] , ["+getYear()+","+getQuarter()+","+getMonth()+","+getDays()+"] , [actualEndMonth = "+getActualCalcEndMonth()+" , planStartMonth = "+getPlanClacStartMonth()+" , endMonthOfYear = "+getEndMonthOfYear()+" , monthOfQuarter() = "+getMonthOfQuarter()+"]";
    }
}
