package com.emailapi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import com.emailapi.service.EmailSendService;
import jakarta.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
class EmailAPIApplicationTests {

	@Test
	void contextLoads() {
	}

	@MockBean
	EmailSendService EmailSendService;

	String to;
	String cc;
	String subject;
	String body;
	String corelationid;
	
	@MockBean 
	JavaMailSender javaMailSender;
	
	@MockBean 
	MimeMessage mimeMessage;

	@Test
	public void sendAttachMailTest() {
		assertEquals(null,
				EmailSendService.sendMail(to, cc, subject, body, corelationid));
	}

}
