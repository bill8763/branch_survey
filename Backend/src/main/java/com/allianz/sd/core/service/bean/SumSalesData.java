package com.allianz.sd.core.service.bean;

public class SumSalesData {
    private SalesData monthSalesData = new SalesData();

    private SalesData quarterSalesData = new SalesData();

    private SalesData yearSalesData = new SalesData();

    public SalesData getMonthSalesData() {
        return monthSalesData;
    }

    public void setMonthSalesData(SalesData monthSalesData) {
        this.monthSalesData = monthSalesData;
    }

    public SalesData getQuarterSalesData() {
        return quarterSalesData;
    }

    public void setQuarterSalesData(SalesData quarterSalesData) {
        this.quarterSalesData = quarterSalesData;
    }

    public SalesData getYearSalesData() {
        return yearSalesData;
    }

    public void setYearSalesData(SalesData yearSalesData) {
        this.yearSalesData = yearSalesData;
    }
}
