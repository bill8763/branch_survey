package com.allianz.sd.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/9
 * Time: 上午 10:04
 * To change this template use File | Settings | File Templates.
 */
public class LoginError extends SnDException  {

    public LoginError(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
