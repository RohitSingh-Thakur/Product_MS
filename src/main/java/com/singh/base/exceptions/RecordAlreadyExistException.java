package com.singh.base.exceptions;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@Component
public class RecordAlreadyExistException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public RecordAlreadyExistException(String msg) {
		super(msg);
	}
}
