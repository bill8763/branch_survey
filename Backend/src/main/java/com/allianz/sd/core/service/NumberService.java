package com.allianz.sd.core.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class NumberService {

    private final int DEFAULT_SCALE = 2;

    public BigDecimal convertToDecimal(double val) {
        return new BigDecimal(val).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
    }

    /**
     * 除
     * @param val1
     * @param val2
     * @return
     */
    public long calcDevideAndRound(long val1 , long val2) {
        double a = (double)val1 / (double)val2;
        return Math.round(a);
    }

    public long round(double val) {
        return Math.round(val);
    }
    public long calcDevideAndfloor(long val1 , long val2) {
        double a = (double)val1 / (double)val2;
        return Math.round(Math.floor(a));
    }
    

    /**
     * 乘
     * @param val1
     * @param val2
     * @return
     */
    public long calcMultipleAndRound(int val1 , int val2) {
        return calcMultipleAndRound((double)val1,(double)val2);
    }

    public long calcMultipleAndRound(int val1 , double val2) {
        return calcMultipleAndRound((double)val1,val2);
    }

    public long calcMultipleAndRound(double val1 , double val2) {
        double a = val1 * val2;
//        System.out.println(a);
        return Math.round(a);
    }
}
