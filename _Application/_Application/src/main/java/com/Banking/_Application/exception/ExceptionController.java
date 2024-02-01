package com.Banking._Application.exception;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.Banking._Application.helper.ResponseStructure;

@ControllerAdvice
public class ExceptionController {
	
	public ResponseEntity<ResponseStructure<String>> idNotFound(MyException ie){
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setCode(HttpStatus.NOT_ACCEPTABLE.value());
		responseStructure.setMessage("Request Failed");
		responseStructure.setData(ie.toString());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_ACCEPTABLE);
		
	}

}
