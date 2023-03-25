package com.masai.exceptionhandler;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.masai.exception.CalenderException;
import com.masai.exception.UserException;

@ControllerAdvice
public class globalExectionHandller {

	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<myErrorDetails> userExceptionHandler(UserException e, WebRequest wr){
		
		myErrorDetails err=new myErrorDetails();
		err.setLocalDate(LocalDate.now());
		err.setName(e.getMessage());
		err.setDesc(wr.getDescription(false));
		
		return new ResponseEntity<myErrorDetails>(err, HttpStatus.BAD_REQUEST);
		
	}
	
	
	@ExceptionHandler(CalenderException.class)
	public ResponseEntity<myErrorDetails> userExceptionHandler(CalenderException e, WebRequest wr){
		
		myErrorDetails err=new myErrorDetails();
		err.setLocalDate(LocalDate.now());
		err.setName(e.getMessage());
		err.setDesc(wr.getDescription(false));
		
		return new ResponseEntity<myErrorDetails>(err, HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<myErrorDetails> masterExceptionHandler(Exception e, WebRequest wr){
		
		myErrorDetails err=new myErrorDetails();
		err.setLocalDate(LocalDate.now());
		err.setName(e.getMessage());
		err.setDesc(wr.getDescription(false));
		
		return new ResponseEntity<myErrorDetails>(err, HttpStatus.BAD_REQUEST);
		
	}

}
