package com.allianz.sd.application.extension.notification;

import com.allianz.sd.application.extension.esb.ESBHandler;
import com.allianz.sd.application.extension.esb.ESBResponse;
import com.allianz.sd.core.handler.RestfulHandler;
import com.allianz.sd.core.handler.bean.RestfulParam;
import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.notification.PushNotification;
import com.allianz.sd.core.service.DateService;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/11
 * Time: 下午 9:07
 * To change this template use File | Settings | File Templates.
 */
public class TWNotification implements PushNotification {

    private Logger logger = LoggerFactory.getLogger(TWNotification.class);

    @Value("${SND.WebService.PushNotification.URL}")
    private String pushNotificationUrl;

    @Autowired
    private DateService dateService;

    @Override
    public void push(Notification notification)  {
        boolean isSuccess = false;

        String pushTime = notification.getPushTime() != null ? dateService.toDateString(notification.getPushTime()) : null;

        for(String pushID : notification.getPushID()) {

            RestfulParam restfulParam = new RestfulParam();
            restfulParam.setUrl(pushNotificationUrl);
            restfulParam.addParam("AgentID",notification.getAgentID());
            restfulParam.addParam("PushID",pushID);
            restfulParam.addParam("PushTitle",notification.getTitle());
            restfulParam.addParam("PushBody",notification.getBody());
            restfulParam.addParam("PushData", notification.getData().toString());

            if(StringUtils.isNotEmpty(pushTime)) {
                restfulParam.addParam("PushTime",pushTime);
            }

            restfulParam.addParam("IsImmediately",notification.isImmediately() ? "Y" : "");

            ESBHandler esbHandler = new ESBHandler();
            ESBResponse response = esbHandler.call(restfulParam,"PushNotification");

            isSuccess = response.isSuccess();

            if(!isSuccess) {
                logger.error("PushNotification Fail!! [errorCode] = " + response.getErrorCode() + ",[errorMsg] = " + response.getErrorMsg());
            }
        }


    }

}
