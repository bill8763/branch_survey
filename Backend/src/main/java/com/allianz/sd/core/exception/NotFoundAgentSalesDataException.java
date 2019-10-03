package com.allianz.sd.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 4:17
 * To change this template use File | Settings | File Templates.
 */

public class NotFoundAgentSalesDataException extends SnDException  {
    public NotFoundAgentSalesDataException() {
        super("B0007","Agent_Sales_Data is not exist");
    }
}
