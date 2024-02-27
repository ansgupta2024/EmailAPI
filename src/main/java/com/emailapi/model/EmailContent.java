package com.emailapi.model;

import lombok.Data;

@Data
public class EmailContent {
	
	private String to;
	private String from;
	private String cc;
	private String subject;
	private String body;

	public EmailContent(String to, String from, String cc,String subject, String body) {
		super();
		this.to = to;
		this.from = from;
		this.cc = cc;
		this.subject = subject;
		this.body = body;
	}

	public EmailContent() {
		super();
	}
}
