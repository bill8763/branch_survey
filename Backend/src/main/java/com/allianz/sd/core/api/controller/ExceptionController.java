package com.allianz.sd.core.api.controller;

import com.allianz.sd.core.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.allianz.sd.core.api.model.Error;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * Created with IntelliJ IDEA.
 * User: Titan
 * Date: 2019/3/16
 * Time: 下午 4:19
 * To change this template use File | Settings | File Templates.
 */
@ControllerAdvice
public class ExceptionController {

	private Logger logger = LoggerFactory.getLogger(ExceptionController.class);

	@ExceptionHandler(SnDException.class)
	public ResponseEntity handleSnDException(HttpServletRequest req, SnDException e) {
		Error error = createError(e.getErrorCode(),e.getErrorMessage());
		return new ResponseEntity(error,HttpStatus.BAD_REQUEST);
	}

	/**
	 * for call API to validation error handling(POJO validation)
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {

		logger.error("handleMethodArgumentNotValidException:" + e.getBindingResult().getTarget().toString(),e);

		Error error = createError("B998",e.getMessage());
		return new ResponseEntity(error,HttpStatus.BAD_REQUEST);
	}

	/**
	 * for call API to validation error handling(Param validation)
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity handleConstraintViolationException(HttpServletRequest req, ConstraintViolationException e){

		logger.error("handleConstraintViolationException",e);

		Error error = createError("B998",e.getMessage());
		return new ResponseEntity(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InvalidResponseFormatException.class)
	public ResponseEntity exception(InvalidResponseFormatException exception) {
		Error error = createError("B998",exception.getMessage());
		return new ResponseEntity(error,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = SystemError.class)
	public ResponseEntity exception(SystemError exception) {
		Error error = createError("B999",exception.getMessage());
		return new ResponseEntity(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = NotFoundContentType.class)
	public ResponseEntity exception(NotFoundContentType exception) {
		Error error = createError("B000","Not Found Content-type in header");
		return new ResponseEntity(error,HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler(value = InvalidTokenException.class)
    public ResponseEntity exception(InvalidTokenException exception) {
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

	protected Error createError(String errorCode , RuntimeException exception) {
		return createError(errorCode,exception.getMessage());
	}

	protected Error createError(String errorCode , String errorMessage) {
		com.allianz.sd.core.api.model.Error error = new Error();

		error.setCode(errorCode);
		error.setMessage(errorMessage);
		error.setSeverityLevel("ERROR");

		return error;
	}
}
