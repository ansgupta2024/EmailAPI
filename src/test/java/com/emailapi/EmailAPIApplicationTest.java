package com.emailapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.emailapi.exception.BusinessException;
import com.emailapi.service.EmailSendService;
import jakarta.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmailAPIApplicationTest {

	@Value("${spring.mail.username}")
	private String fromEmail;

	@InjectMocks
	EmailSendService emailSendService;

	@Mock
	JavaMailSender javaMailSender;

	@MockBean
	MimeMessage mimeMessage;

	@Test
	public void sendMailSuccessTest() {
		ReflectionTestUtils.setField(emailSendService, "fromEmail", "testemail@gmail.com");
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		doNothing().when(javaMailSender).send(any(MimeMessage.class));
		assertEquals("Email Service sendMail without attachment completed successfully",
				emailSendService.sendMail("aaa@gmail.com", "bbb@gmail.com", "test subject", "test body", "abcd12345"));
	}

	@Test
	public void sendMailFailureTest() {
		ReflectionTestUtils.setField(emailSendService, "fromEmail", "testemail@gmail.com");
		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
		doThrow(new BusinessException()).when(javaMailSender).send(any(MimeMessage.class));
		assertThrows(BusinessException.class, () -> {
			emailSendService.sendMail("aaa@gmail.com", "bbb@gmail.com", "test subject", "test body", "abcd12345");
		});
	}

}
