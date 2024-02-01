package com.Banking._Application.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.Banking._Application.model.Customer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailVerification {

	@Autowired
	JavaMailSender mailSender;
	
	public void sendMail(Customer customer) {
		MimeMessage mailMessage=  mailSender.createMimeMessage();
		MimeMessageHelper helper= new MimeMessageHelper(mailMessage);
		
		try {
			helper.setFrom("rudrarithi07@gmail.com");
		}catch(MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			helper.setTo(customer.getEmail());
		}catch(MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			helper.setSubject("Mail Verification");
		}catch(MessagingException e) {
			e.printStackTrace();
		}
		
		try {
			helper.setText("Your OTP For email Verification is "+ customer.getOtp());
			
		}catch(MessagingException e) {
			e.printStackTrace();
		}
		
		mailSender.send(mailMessage);
	}
	
}
