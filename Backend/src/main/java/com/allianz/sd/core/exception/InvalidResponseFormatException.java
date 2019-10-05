package com.allianz.sd.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 4:17
 * To change this template use File | Settings | File Templates.
 */

public class InvalidResponseFormatException extends RuntimeException  {
    public InvalidResponseFormatException(String message) {
        super(message);
    }
}
