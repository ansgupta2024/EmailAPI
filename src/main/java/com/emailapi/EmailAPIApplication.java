package com.emailapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class EmailAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailAPIApplication.class, args);
		log.info("EmailAPIApplication is now up and running..");
	}

}
