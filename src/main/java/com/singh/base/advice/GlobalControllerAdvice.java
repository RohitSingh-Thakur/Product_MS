package com.singh.base.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.singh.base.exceptions.NoRecordFoundByIdException;
import com.singh.base.exceptions.NoRecordFoundWithGivenName;
import com.singh.base.exceptions.NoRecordsFound;
import com.singh.base.exceptions.RecordAlreadyExistException;

@RestControllerAdvice
public class GlobalControllerAdvice{
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handlemethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
		Map<String, String> response = new HashMap<>();
		
		methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error ->{
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			response.put(fieldName, errorMessage);
		});
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoRecordFoundByIdException.class)
	public ResponseEntity<String> handleNoRecordFound(NoRecordFoundByIdException obj) {
		return new ResponseEntity<String>(obj.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoRecordsFound.class)
	public ResponseEntity<String> handleNoRecordsFound(NoRecordsFound obj) {
		return new ResponseEntity<String>(obj.getMessage(), HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleNoRecordExist(IllegalArgumentException obj) {
		return new ResponseEntity<String>(obj.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RecordAlreadyExistException.class)
	public ResponseEntity<String> handleRecordAlreadyExistException(RecordAlreadyExistException obj){
		return new ResponseEntity<String>(obj.getMessage(),HttpStatus.CONFLICT);
	}
	@ExceptionHandler(NoRecordFoundWithGivenName.class)
	public ResponseEntity<String> handleNoRecordFoundWithGivenName(NoRecordFoundWithGivenName obj){
		return new ResponseEntity<String>(obj.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<String> handleNullPointerException(NullPointerException obj){
		return new ResponseEntity<String>(obj.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
}
