package com.allianz.sd.core.datasync;

import java.util.List;

public interface AgentTableDataSync<T> {
    public void pullData(String agentID , List<T> pullData) throws Exception;
}
