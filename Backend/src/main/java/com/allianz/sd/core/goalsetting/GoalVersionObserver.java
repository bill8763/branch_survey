package com.allianz.sd.core.goalsetting;

import com.allianz.sd.core.goalsetting.bean.GoalVersionSubject;
import org.springframework.transaction.annotation.Transactional;

/**
 * 觀察每年GoalVersion的狀態
 * 共有：CREATE(送審並需要審核) / CHANGE(最後Approve的Goal資料需要異動)
 */
public interface GoalVersionObserver {
    @Transactional
    public void onVersionChange(GoalVersionSubject goalVersionSubject) throws Exception;
}
