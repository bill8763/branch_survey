package com.allianz.sd.core.progress;

import java.util.Date;

import com.allianz.sd.core.notification.Notification;

public interface ProgressActivity {
	public Notification getActivityNotification(int point,String agentID,Date date);
	public int getMiniPoint();
}
