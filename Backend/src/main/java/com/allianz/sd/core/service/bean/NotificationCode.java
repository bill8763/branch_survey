package com.allianz.sd.core.service.bean;

/**
 * Enumerated of Notification code
 * can mapping to table language_mapping_text's mapping_id
 */
public enum NotificationCode {
	Approve_Goal_Is_Approved_Title,
	Approve_Goal_Is_Approved_Body,
	Approve_Goal_Is_Reject_Title,
	Approve_Goal_Is_Reject_Body,
	Activity_Ten_Point_Title, //當日點數達10點
	Activity_Ten_Point_Body,
	Activity_Twenty_Point_Title, //當日點數達20點
	Activity_Twenty_Point_Body,
	Activity_Mini_Point_Title, //當日點數未達20點
	Activity_Mini_Point_Body,
	Need_To_Goal_Setting_Title,//需要GoalSetting
	Need_To_Goal_Setting_Body,
	Supervisor_Have_Change_Agent_Title, //GoalSetting主管換人 for Agent
	Supervisor_Have_Change_Agent_Body,
	Supervisor_Have_Change_Old_Title, //GoalSetting主管換人 for old
	Supervisor_Have_Change_Old_Body,
	Supervisor_Have_Change_New_Title, //GoalSetting主管換人 for new 
	Supervisor_Have_Change_New_Body,
	Pending_Review_Title ,  //待審核
	Pending_Review_Body,
    OPUS_TITLE, 		//已刪除客戶資料之推播通知 
    OPUS_BODY,
    Customer_Auto_Delete_Title,
    Customer_Auto_Delete_Body,
	Goal_Auto_Approve_Message_Title, 	//auto approve goal when due time
	Goal_Auto_Approve_Message_Desc, 	//auto approve goal when due time
	Goal_Auto_Approve_Leader_Message_Title, 	//auto approve goal push to leader when due time
	Goal_Auto_Approve_Leader_Message_Desc, 	//auto approve goal push to leader when due time
	Goal_Auto_Reject_Message_Title,			//auto reject goal when due time
	Goal_Auto_Reject_Message_Description,		//auto reject goal when due time
	Goal_Auto_Reject_Leader_Message_Title,				//auto reject goal push to leader when due time
	Goal_Auto_Reject_Leader_Message_Description,		//auto reject goal push to leader when due time
    Customer_Overtime_Title,//客戶超過時間顯示會刪除
    Customer_Overtime_Body,//客戶超過時間顯示會刪除
    Data_Privacy_Protection_Title,//個資保護政策
    Data_Privacy_Protection_Body,//個資保護政策
    You_Have_N_Appointment // Calendar Notification Title 
    ;
	
}