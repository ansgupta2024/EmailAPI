package com.emailapi.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emailapi.exception.BusinessException;
import com.emailapi.model.EmailContent;
import com.emailapi.service.EmailDraftService;
import com.emailapi.service.EmailReadService;
import com.emailapi.service.EmailSendService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/emailservice")
@Slf4j
public class EmailAPIController {

	@Autowired
	private EmailSendService emailSendService;

	@Autowired
	private EmailReadService emailReadService;

	@Autowired
	private EmailDraftService emailDraftService;

	private EmailContent emailContent = new EmailContent();

	@GetMapping("/readinbox")
	public ResponseEntity<Object> retreiveAllMail(@RequestParam("emailID") String userEmailID,
			@RequestParam(name = "startIndex", required = false) String startIndex) {
		String corelationid = UUID.randomUUID().toString();
		log.info("Inside the retreiveAllMail endpoint: " + corelationid);
		try {
			int startInd = 0;
			if (!isEmpty(startIndex)) {
				startInd = Integer.parseInt(startIndex);
			}
			log.info("Inside the retrieveall endpoint..");
			return ResponseEntity.ok(emailReadService.retriveAllService(userEmailID, startInd));
		} catch (Exception e) {
			log.error("Error happened inside sendMail : ", e);
			return new ResponseEntity<>("Unexpected error contidition occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/onemail")
	public ResponseEntity<EmailContent> singleRetrieveMail(@RequestParam String emailID, @RequestParam String subject) {
		String corelationid = UUID.randomUUID().toString();
		log.info("Inside the retrieveall endpoint : " + corelationid);
		return ResponseEntity.ok(emailReadService.singleRetrieveService(emailID, subject));
	}

	@PostMapping("/sendermail")
	public ResponseEntity<String> sendMail(@RequestParam(value = "file", required = false) MultipartFile[] file,
			@RequestParam("to") String to, @RequestParam("cc") String cc, @RequestParam("subject") String subject,
			@RequestParam("body") String body) {
		String corelationid = UUID.randomUUID().toString();
		log.info("Inside the send endpoint: " + corelationid);
		String responsestr = "";
		try {
			if (isEmpty(to) || isEmpty(subject) || isEmpty(body)) {
				return new ResponseEntity<String>("Invalid input provided.Please refer Request ID : " + corelationid,
						HttpStatus.BAD_REQUEST);
			}
			if (file != null) {
				responsestr=emailSendService.sendAttachMail(file, to, cc, subject, body, corelationid);
				responsestr = "Email sent successfully to user : " + to;
				log.info("Email Service sendAttachMail completed successfully : " + corelationid);
			} else {
				responsestr=emailSendService.sendMail(to, cc, subject, body, corelationid);
				log.info("Email Service sendMail without attachment completed successfully : " + corelationid);
			}
			log.info("Email Service sendMail endpoint completed successfully : " + corelationid);

		} catch (BusinessException e) {
			log.error("Error happened inside Service Layer : ", e);
			return new ResponseEntity<String>("Unexpected error contidition occured while connecting to SMTP Server",
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			log.error("Error happened inside sendMail : ", e);
			return new ResponseEntity<String>("Unexpected error contidition occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(responsestr, HttpStatus.OK);
	}

	@PostMapping("/draft")
	public ResponseEntity<String> draftMail(@RequestParam(value = "file", required = false) MultipartFile[] file,
			String emailID, String to, String cc, String subject, String body) {
		String corelationid = UUID.randomUUID().toString();
		log.info("Inside the send endpoint..");
		String responsestr = "";
		try {
			if (file != null) {
				responsestr = emailDraftService.draftAttachMail(file, emailID, to, cc, subject, body);
				log.info("Email Service sendAttachMail completed successfully");
			}
			responsestr = emailDraftService.draftMail(emailID, to, cc, subject, body);
			log.info("Email Service sendMail completed successfully");

		} catch (Exception e) {
			log.error("Error happened inside sendMail : ", e);
			return new ResponseEntity<String>("Error scenario occured", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(responsestr, HttpStatus.OK);
	}

	@PutMapping("/draftupdate")
	public ResponseEntity<String> draftUpdateMail(@RequestParam String to, String emailID, String cc, String subject,
			String body) {
		String corelationid = UUID.randomUUID().toString();
		log.info("Inside the send endpoint..");
		String responsestr = "";
		try {
			emailContent = emailReadService.singleRetrieveService(emailID, subject);
			boolean deleteDraftMail = emailDraftService.draftDeleteMail(emailContent.getFrom(), emailContent.getTo(),
					emailContent.getCc(), emailContent.getSubject(), emailContent.getBody());
			if (deleteDraftMail) {
				emailDraftService.draftMail(emailContent.getFrom(), emailContent.getTo(), emailContent.getCc(),
						emailContent.getSubject(), emailContent.getBody());
			}
			log.info("Email Service sendMail completed successfully");

		} catch (Exception e) {
			log.error("Error happened inside sendMail : ", e);
			return new ResponseEntity<String>("Error scenario occured", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>(responsestr, HttpStatus.OK);
	}

	private boolean isEmpty(String value) {
		return (value == null || value.isEmpty());
	}

}
