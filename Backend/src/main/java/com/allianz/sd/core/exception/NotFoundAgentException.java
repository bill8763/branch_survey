package com.allianz.sd.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 4:17
 * To change this template use File | Settings | File Templates.
 */

public class NotFoundAgentException extends SnDException  {
    public NotFoundAgentException() {
        super("401", "agent is not exist");
    }
}
