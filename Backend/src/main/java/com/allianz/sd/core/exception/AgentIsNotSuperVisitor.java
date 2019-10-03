package com.allianz.sd.core.exception;


public class AgentIsNotSuperVisitor extends SnDException  {


    public AgentIsNotSuperVisitor(String supervisorAgentID, String agentID) {
        super("B0016","This agent["+supervisorAgentID + "] don't have any approve or reject role to ["+agentID+"]");
    }
}
