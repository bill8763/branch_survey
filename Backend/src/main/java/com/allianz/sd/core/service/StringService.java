package com.allianz.sd.core.service;

import org.springframework.stereotype.Service;

@Service
public class StringService {
    public boolean isNumeric(String value) {
        try{
            Double.parseDouble(value);
            return true;
        }catch(Exception e) {
            return false;
        }

    }

    public boolean isNull(String value) {
        return value == null || "null".equalsIgnoreCase(value);
    }

    public boolean isNotNull(String value) {
        return !isNull(value);
    }
}
