package com.java.dto;

public class GenericResponseDto {
	private String message;
	private String error;

	public GenericResponseDto(String message) {
	        super();
	        this.setMessage(message);
	    }

	public GenericResponseDto(String message, String error) {
	        super();
	        this.setMessage(message);
	        this.setError(error);
	    }

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
