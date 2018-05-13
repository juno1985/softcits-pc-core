package org.softcits.pc.core.exceptionHandler;

import java.util.HashMap;
import java.util.Map;

import org.softcits.pc.core.exception.PC404Exception;
import org.softcits.pc.core.exception.PC4XXException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
@ControllerAdvice
public class PCExceptionHandler {

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(PC4XXException.class)
	public ResponseEntity<Map> PC4XXExceptionHandler(PC4XXException pc4XXException){
		Map<String, String> result = new HashMap<String, String>();
		result.put("msg", pc4XXException.getMessage());
		return new ResponseEntity<Map>(result, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("rawtypes")
	@ExceptionHandler(PC404Exception.class)
	public ResponseEntity<Map> PC404ExceptionHandler(PC404Exception pc4XXException){
		Map<String, String> result = new HashMap<String, String>();
		result.put("msg", pc4XXException.getMessage());
		return new ResponseEntity<Map>(result, HttpStatus.NOT_FOUND);
	}
}
