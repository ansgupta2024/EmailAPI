package com.emailapi.service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emailapi.exception.BusinessException;

@Service
@Slf4j
public class EmailSendService {

	@Value("${spring.mail.username}")
	private String fromEmail;

	private JavaMailSender javaMailSender;

	public EmailSendService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String sendAttachMail(MultipartFile[] file, String to, String cc, String subject, String body,
			String corelationid) throws BusinessException {
		try {
			log.info("Inside sendAttachMail() service call : " + corelationid);
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

			javaMailSender.send(mimeMessage);
			log.info("Email with attachment sent successfully : " + corelationid);
			return "Email Service sendMail completed successfully";
		} catch (Exception e) {
			throw new BusinessException("601", "Unexpected error contidition occured while connecting to SMTP Server");
		}

	}

	public String sendMail(String to, String cc, String subject, String body, String corelationid)
			throws BusinessException {
		log.info("Inside sendMail() service call : " + corelationid);
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(fromEmail);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setCc(cc);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body);

			javaMailSender.send(mimeMessage);
			log.info("Email with attachment sent successfully : " + corelationid);
			return "Email Service sendMail without attachment completed successfully";
		} catch (Exception e) {
			throw new BusinessException("601", "Unexpected error contidition occured while connecting to SMTP Server");
		}
	}
}
