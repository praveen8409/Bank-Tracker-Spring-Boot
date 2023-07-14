package com.bank.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bank.controllers.CustomerController;

@ControllerAdvice
public class ExceptionHanddler {

	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@ExceptionHandler(value = AccountNotFoundException.class)
	public ResponseEntity<Object> exception(AccountNotFoundException exception){
		logger.error("An Exception occured!", new Exception("Account Not Found :("));
		return new ResponseEntity<Object>("Account Not Found !!",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = CustomerNotFoundException.class)
	public ResponseEntity<Object> exception(CustomerNotFoundException exception){
		logger.error("An Exception occured!", new Exception("Customer Not Found :("));
		return new ResponseEntity<Object>("Customer Not Found !!",HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = IdNotFoundException.class)
	public ResponseEntity<Object> exception(IdNotFoundException exception){
		logger.error("An Exception occured!", new Exception("Id Not Found :("));
		return new ResponseEntity<Object>("Id Not Found !!",HttpStatus.NOT_FOUND);
	}
	
}
