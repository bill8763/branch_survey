package com.allianz.sd.core.progress;

import com.allianz.sd.core.service.bean.AgentDailyAppDateType;

public interface CalcAcvitity {
	public void calc(int val , double pointBase, AgentDailyAppDateType dataType, boolean isReplace);
}
