package com.allianz.sd.core.service.bean;

import java.math.BigDecimal;

public class PersonalActivity {
    private BigDecimal[] goalValues = new BigDecimal[5];
    private BigDecimal[] actualValues = new BigDecimal[5];

    public BigDecimal[] getGoalValues() {
        return goalValues;
    }

    public void setGoalValues(BigDecimal[] goalValues) {
        this.goalValues = goalValues;
    }

    public BigDecimal[] getActualValues() {
        return actualValues;
    }

    public void setActualValues(BigDecimal[] actualValues) {
        this.actualValues = actualValues;
    }
}
