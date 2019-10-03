package com.allianz.sd.core.goalsetting;

import com.allianz.sd.core.goalsetting.bean.GoalStatusSubject;
import com.allianz.sd.core.service.bean.GoalSettingStatus;
import org.apache.commons.collections4.map.MultiKeyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class GoalStatusListener {
    private Logger logger = LoggerFactory.getLogger(GoalStatusListener.class);

    private MultiKeyMap ruleMap = new MultiKeyMap();

    public void addObserver(GoalSettingStatus from , GoalSettingStatus to , GoalStatusChangeHandler handler) {


        List<GoalStatusChangeHandler> handlers = (List<GoalStatusChangeHandler>) ruleMap.get(from,to);
        if(handlers == null) handlers = new ArrayList<>();

        handlers.add(handler);

        logger.info("Register GoalStatusListener["+from+","+to+","+handler.getClass().getName()+"]");

        ruleMap.put(from,to,handlers);
    }

    @Transactional
    public void changeStatus(GoalStatusSubject goalStatusSubject) throws Exception{
        this.notifyHandler(goalStatusSubject);
    }

    @Transactional
    public void notifyHandler(GoalStatusSubject goalStatusSubject) throws Exception {

        GoalSettingStatus from = goalStatusSubject.getBeforeStatus();
        GoalSettingStatus to = goalStatusSubject.getAfterStatus();

        logger.trace("notifyObservers rule = [" + from + "," + to + "]");
        List<GoalStatusChangeHandler> handlers = (List<GoalStatusChangeHandler>) ruleMap.get(from,to);
        if(handlers != null) {
            logger.trace("notifyObservers handlers size = " + handlers.size());

            for(GoalStatusChangeHandler handler : handlers) {
                logger.trace("notify handler["+handler.getClass().getName()+"]");
                handler.onStatusChange(goalStatusSubject);
            }
        }
        else {
            logger.debug("No Match GoalStatusListener rule["+from+","+to+"]");
        }

    }
}
