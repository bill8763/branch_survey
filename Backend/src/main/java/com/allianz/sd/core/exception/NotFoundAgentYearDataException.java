package com.allianz.sd.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 4:17
 * To change this template use File | Settings | File Templates.
 */

public class NotFoundAgentYearDataException extends SnDException  {


    public NotFoundAgentYearDataException(String organizationalUnit,String agentID,int dataYear) {
        super("B0003", "Agent_Year_Data is not exist:["+organizationalUnit+"]["+agentID+"]["+dataYear+"]");

    }

}
