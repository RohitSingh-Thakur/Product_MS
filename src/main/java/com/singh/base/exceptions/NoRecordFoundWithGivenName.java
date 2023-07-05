package com.singh.base.exceptions;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class NoRecordFoundWithGivenName extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public NoRecordFoundWithGivenName(String msg) {
		super(msg);
	}
}
