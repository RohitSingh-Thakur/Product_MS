package com.singh.base.exceptions;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class NoRecordsFound extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public NoRecordsFound(String msg) {
		super(msg);
	}
}
