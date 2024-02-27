package com.emailapi.model;

import java.util.List;

import lombok.Data;

@Data
public class EmailMailBoxContents {

	private int totalnumberOfEmails;
	private int pageNo;
	private int pageItemSize;
	private List<EmailContent> emailContents;
}
