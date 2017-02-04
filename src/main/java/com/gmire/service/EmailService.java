package com.gmire.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private static Logger LOG = LoggerFactory.getLogger(EmailService.class);
	
	//@Autowired
	//private JavaMailSender javaMailSender;
	
	@Async
	public void sendNotification(String fromEmail, String toEmail, String subject, String text) {//throws MailException, InterruptedException {
		
        LOG.info("Sending email to " + toEmail + "...");
        
        SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(toEmail);
		mail.setFrom(fromEmail);
		mail.setSubject(subject);
		mail.setText(text);
		//javaMailSender.send(mail);
		
		LOG.info("Email Sent!");
	}
}
