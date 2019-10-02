package com.allianz.sd.core.controller;

import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.service.NotificationsService;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/1/23
 * Time: 下午 4:04
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/notification")
public class PushNotificationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NotificationsService notificationsService;

    @RequestMapping(value = "/send", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Boolean> send(@RequestBody Notification notificationObj) throws JSONException {

        JSONObject data = new JSONObject();
        data.put("XXXX", "xxx");
        data.put("type", "calendar");
        data.put("color","#496ebd");
        data.put("location","會議室");
        data.put("start","2018-02-15T14:20:00");
        data.put("end","2018-02-15T14:50:00");

        notificationObj.setData(data);

        notificationsService.push(notificationObj);

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
