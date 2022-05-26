package com.java.model;

import java.time.Instant;

public class Message {
	private String message;
	private Instant timestamp;
	private String status;
    private String error;
    private String path;
	public Message(String message, Instant timestamp, String status, String error, String path) {
		this.message = message;
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.path  = path;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public Instant getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
