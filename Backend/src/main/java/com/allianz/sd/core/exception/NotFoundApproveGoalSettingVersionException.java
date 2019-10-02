package com.allianz.sd.core.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 4:17
 * To change this template use File | Settings | File Templates.
 */

public class NotFoundApproveGoalSettingVersionException extends SnDException  {


    public NotFoundApproveGoalSettingVersionException(String organizationalUnit,String agentID,int dataYear) {
        super("B0009", "Appgove Goal Setting Version is not exist:["+organizationalUnit+"]["+agentID+"]["+dataYear+"]");

    }

}
