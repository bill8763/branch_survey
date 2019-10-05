package com.allianz.sd.core.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 12:28
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TokenService {

    private Logger logger = LoggerFactory.getLogger(TokenService.class);

    @Value("${jwt.secret}")
    private String secret = "1234567890123456789012345678901234567890123456789012345678901234";

    @Value("${jwt.expirationMin}")
    private String expirationMin = "1440";

    @Autowired
    private DateService dateService = new DateService();

    public static void main(String[] args) throws Exception{

        Set<String> set = new HashSet<>();
        set.add("AG");

        Map<String, Object> claims = new HashMap<>();
        claims.put("AgentID",1);
        claims.put("AgentName","John");
        claims.put("GoalSigningSupervisor",2);
        claims.put("AppUseMode",set);

        TokenService tokenService = new TokenService();
        String token = tokenService.generateToken(claims);

        System.out.println(token);
        System.out.println(tokenService.getExpirationDate(token));
        System.out.println(tokenService.getUsernameFromToken(token));
    }

    public Date getExpirationDate(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    private Claims parseToken(String token) {

        try{

            if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")){
                token = token.substring(7);
            }

            String base64Key = DatatypeConverter.printBase64Binary(secret.getBytes());
            byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);

            return Jwts.parser()
                    .setSigningKey(secretBytes)
                    .parseClaimsJws(token)
                    .getBody();
        }catch(Exception e) {
            logger.error("jwt token parse is error[" + token + "] , ErrorMsg:["+e.getMessage()+"]");
        }

        return null;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? String.valueOf(parseToken(token).get("AgentName")) : null;
    }

    public String getAgentIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? String.valueOf(parseToken(token).get("AgentID")) : null;
    }

    public String generateToken(Map<String, Object> claims) {

        return generateToken(claims,true);
    }

    public String generateToken(Map<String, Object> claims , boolean isExpirationToken) {

        String base64Key = DatatypeConverter.printBase64Binary(secret.getBytes());
        byte[] secretBytes = DatatypeConverter.parseBase64Binary(base64Key);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secretBytes);

        if(isExpirationToken) jwtBuilder.setExpiration(generateExpirationDate());

        return jwtBuilder.compact();

    }

    private Date generateExpirationDate() {
        return dateService.addDate(new Date(), Calendar.MINUTE,Integer.parseInt(expirationMin));
    }
}
