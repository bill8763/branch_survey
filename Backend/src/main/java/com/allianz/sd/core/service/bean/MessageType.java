package com.allianz.sd.core.service.bean;

/**
 * Enumerated of Notification code
 * can mapping to table language_mapping_text's mapping_id
 */
public enum MessageType {
	ActivityArriveTenPoint, // Jeff
	ActivityArriveTwentyPoint,
	ActivityLessThanTwentyPoint,
	GoalPromoSetting,
	SupervisorHaveChangeAgent, 
	SupervisorHaveChangeNew,
	SupervisorHaveChangeOld, 
	GoalAutoReject,						// goal auto Reject
	GoalAutoRejectLeader,				// goal auto Reject leader
	GoalAutoApproveLeader,
	GoalAutoApprove,
	ApproveGoalIsReject,
	ApproveGoalIsApprove,
	Need_Goal_Approve,
	Overtime, // Customer no maintain customer
	AutoDelete,
	PendingReview,
	ReminderEvent,
	DataPrivacyProtection, //every quarter send notification check privacy
    ;
	public static MessageType getMessageType(NotificationCode code) {
		
		switch(code) {
			case Activity_Ten_Point_Title:
				return ActivityArriveTenPoint;
			case Activity_Twenty_Point_Title:
				return ActivityArriveTwentyPoint;
			case Activity_Mini_Point_Title :
				return ActivityLessThanTwentyPoint;
		}
		return null;
	}
}