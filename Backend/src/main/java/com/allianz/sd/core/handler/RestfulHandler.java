package com.allianz.sd.core.handler;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import com.allianz.sd.core.handler.bean.RestfulParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/4/9
 * Time: 上午 11:40
 * To change this template use File | Settings | File Templates.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RestfulHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestfulHandler.class);

    @Value("${server.ssl.key-store:}")
	private String keyStore;

	@Value("${server.ssl.key-store-password:}")
	private String keyStorePassword;

	@Value("${server.ssl.keyStoreType:}")
	private String keyStoreType;

	@Autowired
	private ResourceLoader resourceLoader;

    public JSONObject callRestfulAPIToObject(RestfulParam param) {
        JSONObject jsonObject = null;

        try{
            String body = toBodyString(param);
            jsonObject = new JSONObject(body);
        }catch(Exception e) {
            logger.error("callResfulAPI response is not JSON Array format!!",e);
        }

        return jsonObject;
    }

    public JSONObject callRestfulAPIToObjectByGet(RestfulParam param) throws Exception {
        JSONObject jsonObject = null;

        String body = null;

        try{
            body = toBodyStringByGet(param);
        }catch(Exception e) {
            throw new Exception("Can't get Response from " + param.toGetURL());
        }

        if(StringUtils.isNotEmpty(body)) {
            try{
                jsonObject = new JSONObject(body);
            }catch(Exception e) {
                logger.error("callResfulAPI response is not JSON Array format!!["+body+"]",e);
            }
        }

        return jsonObject;
    }

    private String toBodyStringByGet(RestfulParam param) {

        String getURL = param.toGetURL();
        Map<String,String> headerMap = param.getHeaderMap();

        RestTemplate restTemplate = new RestTemplate();
        logger.trace("start call API  = " + getURL);

        //set header
        HttpHeaders headers = new HttpHeaders();
        for(String headerKey : headerMap.keySet()) {
            headers.add(headerKey,headerMap.get(headerKey));
        }

        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = restTemplate.exchange(getURL, HttpMethod.GET, entity, String.class);

        String body = response.getBody();
        logger.trace("response JSON = " + body);

        return body;
    }


    private String toBodyString (RestfulParam param) {

        String url = param.getUrl();
        Map<String,String> headerMap = param.getHeaderMap();
        MultiValueMap<String, String> map = param.getMap();

        if(StringUtils.isEmpty(url)) {
            logger.error("callRestfulAPI url is empty!!");
        }

        RestTemplate restTemplate = new RestTemplate();
        
        //SSL connection
        //SSL-Socket Factory
        SSLConnectionSocketFactory ssLSocketFactory = null;
		try {
			logger.debug("keyStore:"+keyStore);
            File keyStoreFile = resourceLoader.getResource(keyStore).getFile();
			FileInputStream keyin = new FileInputStream(keyStoreFile);
            KeyStore keystore = KeyStore.getInstance(keyStoreType);
            char[] password = keyStorePassword.toCharArray();
            
            keystore.load(keyin, password);
            keyin.close();
            final TrustStrategy trustStrategy = new TrustStrategy()  {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            };
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(trustStrategy)
                    .loadKeyMaterial(keystore, password)
                    .build();
            ssLSocketFactory = new SSLConnectionSocketFactory(
                    sslcontext,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
			
		} catch (Exception e) {
			logger.error("SSL connection error:"+e.getLocalizedMessage());
			e.printStackTrace();
		}
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClients.custom().setSSLSocketFactory(ssLSocketFactory).build()
        );
        httpRequestFactory.setReadTimeout(5000);
        httpRequestFactory.setConnectTimeout(15000);
        httpRequestFactory.setConnectionRequestTimeout(15000);
        restTemplate.setRequestFactory(httpRequestFactory);

        //set header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        for(String headerKey : headerMap.keySet()) {
            headers.add(headerKey,headerMap.get(headerKey));
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        logger.trace("start call API  = " + url);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request , String.class );

        String body = response.getBody();
        logger.trace("response JSON = " + body);

        return body;
    }
}
