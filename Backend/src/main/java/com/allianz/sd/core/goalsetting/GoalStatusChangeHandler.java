package com.allianz.sd.core.goalsetting;

import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import org.springframework.transaction.annotation.Transactional;

/**
 * Goal Status Machine
 */
public interface GoalStatusChangeHandler {

    @Transactional
    public void onStatusChange(GoalStatusSubject goalStatusSubject) throws Exception;
}
