package com.allianz.sd.application.extension.esb;

import com.allianz.sd.core.handler.bean.RestfulParam;
import com.allianz.sd.core.jpa.model.ESBLog;
import com.allianz.sd.core.jpa.repository.ESBLogRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.allianz.sd.core.handler.RestfulHandler;
import com.allianz.sd.core.service.DateService;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/10
 * Time: 上午 9:34
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ESBHandler {

    private static final Logger logger = LoggerFactory.getLogger(ESBHandler.class);

    @Autowired
    private ESBLogRepository esbLogRepository;

    @Autowired
    private DateService dateService;

    @Autowired
    private RestfulHandler handler;
    
    public ESBResponse call(RestfulParam restfulParam, String txId) {

        ESBResponse response = new ESBResponse();

        //儲存一筆log
        ESBLog esbLog = new ESBLog();
        esbLog.setTxId(txId);
        esbLog.setRequest(restfulParam.request2JSONFormat());
        esbLog.setCreateTime(dateService.getTodayDate());

        logger.trace("Start call ESB API["+txId+"]");
        JSONObject jsonObject = handler.callRestfulAPIToObject(restfulParam);
        if(jsonObject != null) {
            String responseCode = jsonObject.getString("responseCode");
            String reasonCode = jsonObject.getString("reasonCode");
            String reasonMsg = jsonObject.getString("reasonMsg");

            if("000".equalsIgnoreCase(responseCode)) {
                response.setSuccess(true);
                response.setBody(jsonObject);
            }
            else if("008".equalsIgnoreCase(responseCode)) {
                response.setErrorCode(reasonCode);
                response.setErrorMsg(reasonMsg);
            }
            else {
                response.setErrorCode(responseCode);
                response.setErrorMsg("系統錯誤訊息");
            }


            esbLog.setResponseCode(responseCode);
            esbLog.setResponse(jsonObject.toString());
        }

        logger.trace("response",response);

//        esbLogRepository.save(esbLog);

        return response;
    }

}
