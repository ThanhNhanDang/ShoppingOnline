package com.java.dto;

public class EmailDto {
	private String from;
	private String to;
	private String subject;
	private String htmlContent;

	public EmailDto(String from, String to, String subject, String htmlContent) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.htmlContent = htmlContent;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	
	

}
