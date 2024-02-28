package com.emailapi.service;

import jakarta.mail.Flags;
import jakarta.mail.Flags.Flag;
import jakarta.mail.Folder;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emailapi.exception.BusinessException;

@Service
@Slf4j
public class EmailDraftService {

	@Value("${spring.mail.username}")
	private String fromEmail;

	@Value("${spring.mail.password}")
	private String fromPwd;

	@Autowired
	private JavaMailSender javaMailSender;

	public String draftMail(String emailID, String to, String cc, String subject, String body, String corelationid) throws BusinessException{
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setCc(cc);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);

			Properties properties = new Properties();
			properties.setProperty("mail.imaps.host", "imap.gmail.com");
			properties.setProperty("mail.imaps.port", "993");
			properties.setProperty("mail.transport.protocol", "IMAP");
			properties.setProperty("mail.imap.ssl.trust", "*");
			Session emailSession = Session.getDefaultInstance(properties);
			emailSession.setDebug(true);

			Store imapsStore = emailSession.getStore("imaps");
			imapsStore.connect("imap.gmail.com", fromEmail, fromPwd);
			Folder draftsMailBoxFolder = imapsStore.getFolder("[Gmail]/Drafts");
			draftsMailBoxFolder.open(Folder.READ_WRITE);
			mimeMessage.setFlag(Flag.DRAFT, true);
			MimeMessage draftMessages[] = { mimeMessage };
			draftsMailBoxFolder.appendMessages(draftMessages);

			return "Drafted Email saved successfully in mailbox..";

		} catch (Exception e) {
			throw new BusinessException("601", "Unexpected error contidition occured while connecting to SMTP Server : "+corelationid);
		}

	}
	
	public boolean draftEmailUpdateService(String emailID,String to, String cc, String subject, String body, String corelationid) throws BusinessException {
		
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setCc(cc);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);

			Properties properties = new Properties();
			properties.setProperty("mail.imaps.host", "imap.gmail.com");
			properties.setProperty("mail.imaps.port", "993");
			properties.setProperty("mail.transport.protocol", "IMAP");
			properties.setProperty("mail.imap.ssl.trust", "*");
			Session emailSession = Session.getDefaultInstance(properties);
			emailSession.setDebug(true);

			Store imapsStore = emailSession.getStore("imaps");
			imapsStore.connect("imap.gmail.com", fromEmail, fromPwd);
			Folder draftsMailBoxFolder = imapsStore.getFolder("[Gmail]/Drafts");
			draftsMailBoxFolder.open(Folder.READ_WRITE);
			mimeMessage.setFlag(Flags.Flag.DELETED, true);
			return true;

		} catch (Exception e) {
			throw new BusinessException("601", "Unexpected error contidition occured while connecting to SMTP Server : "+corelationid);		
		}
	}


	public String draftAttachMail(MultipartFile[] file, String emailID, String to, String cc, String subject, String body, String corelationid)
			throws BusinessException{
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setCc(cc);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);

			for (int i = 0; i < file.length; i++) {
				mimeMessageHelper.addAttachment(file[i].getOriginalFilename(),
						new ByteArrayResource(file[i].getBytes()));
			}

			Properties properties = new Properties();
			properties.setProperty("mail.imaps.host", "imap.gmail.com");
			properties.setProperty("mail.imaps.port", "993");
			properties.setProperty("mail.transport.protocol", "IMAP");
			properties.setProperty("mail.imap.ssl.trust", "*");
			Session emailSession = Session.getDefaultInstance(properties);
			emailSession.setDebug(true);

			Store imapsStore = emailSession.getStore("imaps");
			imapsStore.connect("imap.gmail.com", fromEmail, fromPwd);
			Folder draftsMailBoxFolder = imapsStore.getFolder("[Gmail]/Drafts");
			draftsMailBoxFolder.open(Folder.READ_WRITE);
			mimeMessage.setFlag(Flag.DRAFT, true);
			MimeMessage draftMessages[] = { mimeMessage };
			draftsMailBoxFolder.appendMessages(draftMessages);
			log.info("Drafted Email with attachment saved successfully in mailbox : " +corelationid);
			return "Drafted Email with attachment saved successfully in mailbox..";

		} catch (Exception e) {
			throw new BusinessException("601", "Unexpected error contidition occured while connecting to SMTP Server : "+corelationid);
		}
	}
}