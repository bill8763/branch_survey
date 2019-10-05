package com.allianz.sd.core.exception;

public abstract class SnDException extends RuntimeException{

    private String errorCode = null;
    private String errorMessage = null;

    public SnDException(String errorCode,String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
