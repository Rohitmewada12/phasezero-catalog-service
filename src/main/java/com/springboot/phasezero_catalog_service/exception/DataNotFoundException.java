package com.springboot.phasezero_catalog_service.exception;

public class DataNotFoundException extends RuntimeException{
	public DataNotFoundException(String message) {
		super(message);
	}
}
