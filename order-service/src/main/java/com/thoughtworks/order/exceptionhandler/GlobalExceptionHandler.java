package com.thoughtworks.order.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.thoughtworks.order.errorresponse.ErrorResponse;
import com.thoughtworks.order.userexception.ProductNotFound;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ErrorResponse handleConstraintViolationException(ConstraintViolationException ex) {

		List<String> errorList = new ArrayList<>();
		ex.getConstraintViolations().forEach(action -> errorList.add(action.getMessage()));

		return new ErrorResponse(errorList, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = ProductNotFound.class)
	public ErrorResponse handleProductNotFoundException(ProductNotFound ex) {

		List<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());

		return new ErrorResponse(errorList, HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = Exception.class)
	public ErrorResponse handleGlobalException(Exception ex) {

		List<String> errorList = new ArrayList<>();
		errorList.add(ex.getMessage());

		return new ErrorResponse(errorList, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
