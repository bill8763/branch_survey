package com.allianz.sd.core.api;

import com.allianz.sd.core.exception.InvalidResponseFormatException;
import com.allianz.sd.core.service.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Aspect
@Component
public class ControllerResponseValidator {

    private Logger logger = LoggerFactory.getLogger(ControllerResponseValidator.class);

    @Autowired
    private Validator validator;

    @Autowired
    private TokenService tokenService;

    @AfterReturning(pointcut = "execution(* com.allianz.sd.core.api.controller.*.*(..))", returning = "result")
    public void validateResponse(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();

        String token = null;
        for(Object arg : args) {
            if(arg != null) {
                String argVal = String.valueOf(arg);

                if(argVal.contains("Bearer")) token = argVal;
            }

        }

        validateResponse(token,result);
    }

    private void validateResponse(String token , Object object) {

        Set<ConstraintViolation<Object>> validationResults = validator.validate(object);

        if (validationResults.size() > 0) {

            StringBuffer sb = new StringBuffer();

            for (ConstraintViolation<Object> error : validationResults) {
                sb.append(error.getPropertyPath()).append(" - ").append(error.getMessage()).append("\n");
            }

            String msg = sb.toString();

            String agentID = null;
            try{
                agentID = tokenService.getAgentIdFromToken(token);
            }catch(Exception e) {}

            logger.error("InvalidResponseFormatException ["+agentID+"] object = " + object);

            throw new InvalidResponseFormatException(msg);
//            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, msg);
        }
    }
}
