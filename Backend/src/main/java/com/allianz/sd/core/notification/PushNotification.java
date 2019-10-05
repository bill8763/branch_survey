package com.allianz.sd.core.notification;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 8:37
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface PushNotification {

    @Async
    public void push(Notification notification) ;

}
