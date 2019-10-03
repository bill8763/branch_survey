package com.allianz.sd.core.impl;

import java.nio.charset.Charset;
import java.util.ArrayList;

import com.allianz.sd.core.interceptor.HeaderRequestInterceptor;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.notification.Notification;
import com.allianz.sd.core.notification.PushNotification;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/2/21
 * Time: 下午 8:42
 * To change this template use File | Settings | File Templates.
 */

@Component
public class FirebasePushNotification implements PushNotification{

    private Logger logger = LoggerFactory.getLogger(FirebasePushNotification.class);


    @Value("${firebase.serverkey}")
    private String FIREBASE_SERVER_KEY = "AAAAIquSbZs:APA91bEs6GPhqLJ8XclRsjl-ukZ8amMz1wOqW3FeIZF5PDEw7Szufko3FijKE74WqN2S26HjjSpB9UxOUC5JC3ORAbYC4UrB527bguSsP9RijmR3jvV6JwQKX3YaNAxE1xZPvg2LfcNR";

    @Value("${firebase.apiurl}")
    private String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";


    @Override
    @Async
	public void push(Notification notification) {

        JSONObject notificationJSON = new JSONObject();
        notificationJSON.put("title",notification.getTitle());
        notificationJSON.put("body", notification.getBody());

        JSONObject firebaseBody = new JSONObject();
        firebaseBody.put("priority", "high");
        firebaseBody.put("notification", notificationJSON);
        firebaseBody.put("data", notification.getData());

        for(String pushID : notification.getPushID()) {

            try{
                firebaseBody.put("to",pushID);

                logger.info("body = " + firebaseBody.toString());

                HttpEntity<String> request = new HttpEntity<>(firebaseBody.toString());

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

                ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
                interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
                interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));

                restTemplate.setInterceptors(interceptors);

                logger.info("send to firebase");

                String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, request, String.class);

                logger.debug("firebaseResponse = " + firebaseResponse );
            }catch(Exception e) {
                logger.error("Push firebase fail!! firebaseBody = ["+firebaseBody.toString()+"]",e);
            }

        }


	}
}
