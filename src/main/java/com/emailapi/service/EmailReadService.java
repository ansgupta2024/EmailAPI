package com.emailapi.service;

import com.emailapi.exception.BusinessException;
import com.emailapi.model.EmailContent;
import com.emailapi.model.EmailMailBoxContents;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailReadService {

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${spring.mail.password}")
	private String fromPwd;

	private List<EmailContent> emailContentObj = new ArrayList<EmailContent>();

	public EmailMailBoxContents readInboxService(String emailID, String corelationid) throws BusinessException {
		final EmailMailBoxContents emailMailBoxContentsObj = new EmailMailBoxContents();
		log.info("Inside the retrieveall endpoint : " + corelationid);
		try {
			/*
			 * Code snippet to connect and fetch Message Object from Email Server
			 * 
			 * 
			 * 
			 * Properties properties = new Properties();
			 * properties.setProperty("mail.imaps.host", "imap.gmail.com");
			 * properties.setProperty("mail.imaps.port", "993");
			 * properties.setProperty("mail.transport.protocol", "IMAP");
			 * properties.setProperty("mail.imap.ssl.trust", "*"); Session emailSession =
			 * Session.getDefaultInstance(properties); emailSession.setDebug(true);
			 * 
			 * Store store = emailSession.getStore("imaps");
			 * 
			 * store.connect("imap.gmail.com", fromEmail, fromPwd);
			 * 
			 * 
			 * Folder emailFolder = store.getFolder("INBOX");
			 * emailFolder.open(Folder.READ_ONLY);
			 * 
			 * BufferedReader reader = new BufferedReader(new InputStreamReader(
			 * System.in));
			 * 
			 * Message[] messages = emailFolder.getMessages();
			 * 
			 * for (int i = 0; i < messages.length; i++) { Message message = messages[i];
			 * if(message.getSubject.equals(subject))
			 * emailContent.setSubject(message.getSubject());
			 * emailContent.setBody(message.getContent());
			 * emailContentObj.add(emailContent); } emailFolder.close(false); store.close();
			 */
			emailMailBoxContentsObj.setEmailContents(emailContentObj);
			log.info("Completed the setEmailContents() successfully : " + corelationid);
			return emailMailBoxContentsObj;
		} catch (Exception e) {
			throw new BusinessException("601", "Unexpected error contidition occured while connecting to SMTP Server : "+corelationid);
		}

	}

	public EmailContent readOneEmailService(String emailID, String subject, String corelationid)
			throws BusinessException {
		log.info("Inside the singleRetrieveService endpoint : " + corelationid);
		try {
			/*
			 * Code snippet to connect and fetch Message Object from Email Server
			 * 
			 * 
			 * 
			 * Properties properties = new Properties();
			 * properties.setProperty("mail.imaps.host", "imap.gmail.com");
			 * properties.setProperty("mail.imaps.port", "993");
			 * properties.setProperty("mail.transport.protocol", "IMAP");
			 * properties.setProperty("mail.imap.ssl.trust", "*"); Session emailSession =
			 * Session.getDefaultInstance(properties); emailSession.setDebug(true);
			 * 
			 * Store store = emailSession.getStore("imaps");
			 * 
			 * store.connect("imap.gmail.com", fromEmail, fromPwd);
			 * 
			 * 
			 * Folder emailFolder = store.getFolder("INBOX");
			 * emailFolder.open(Folder.READ_ONLY);
			 * 
			 * BufferedReader reader = new BufferedReader(new InputStreamReader(
			 * System.in));
			 * 
			 * Message[] messages = emailFolder.getMessages();
			 * 
			 * for (int i = 0; i < messages.length; i++) { Message message = messages[i];
			 * if(message.getSubject.equals(subject)){
			 * emailContent.setSubject(message.getSubject());
			 * emailContent.setBody(message.getContent());
			 * emailContentObj.add(emailContent); } emailFolder.close(false); store.close();
			 */
			EmailContent emailContent = new EmailContent("xxxx.yyyy@gmail.com", "abcd0.efgh0@gmail.com",
					"mnlop0.qrst0@gmail.com", "Subj 1", "Email Content is here for Email #1");
			log.info("Completed the singleRetrieveService successfully : " + corelationid);
			return emailContent;
		} catch (Exception e) {
			throw new BusinessException("601", "Unexpected error contidition occured while connecting to SMTP Server : "+corelationid);
		}
		
	}
	
	{
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd0.efgh0@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 1", "Email Content is here for Email #1"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd1.efgh1@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 2", "Email Content is here for Email #2"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd2.efgh2@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 2", "Email Content is here for Email #3"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd3.efgh3@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 3", "Email Content is here for Email #4"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd4.efgh4@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 4", "Email Content is here for Email #5"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd0.efgh0@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 6", "Email Content is here for Email #6"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd1.efgh1@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 7", "Email Content is here for Email #7"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd2.efgh2@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 8", "Email Content is here for Email #8"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd3.efgh3@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 9", "Email Content is here for Email #9"));
		emailContentObj.add(new EmailContent("xxxx.yyyy@gmail.com", "abcd4.efgh4@gmail.com", "mnlop0.qrst0@gmail.com",
				"Subj 10", "Email Content is here for Email #10"));
	}

}
