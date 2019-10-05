package com.allianz.sd.core.authorization;

import com.allianz.sd.core.authorization.bean.DeviceInfo;
import com.allianz.sd.core.authorization.bean.LoginResponse;

import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/5
 * Time: 上午 11:43
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface Authorization {

    //valid username & password is valid and exist
    public LoginResponse login(String username , String password,DeviceInfo deviceInfo);



}
