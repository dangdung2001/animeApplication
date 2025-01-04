package com.app.animeApplication.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.app.animeApplication.config.appConstants;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

	@Autowired
    private JavaMailSender mailSender;

	
	
    public void sendEmailSimple(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }
    
    public Long sendEmailMIME(String to, String subject, String text) throws MessagingException {
    	
    	Long code = randomCODE();
    	
    	final String HTML_SEND_MAIL = "<h1>Welcome to ANIME WEBSITE</h1><p>Code : <b>"
    			+ code + "</b></p>";
    	
    	MimeMessage mimeMail = this.mailSender.createMimeMessage();
    	
    	MimeMessageHelper helper = new MimeMessageHelper(mimeMail , "UTF-8");
    	
    	helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(HTML_SEND_MAIL, true); 

        mailSender.send(mimeMail);
        
        return code;
    }
    
    
    public static Long randomCODE() {
    	Random random = new Random();
    	return random.nextLong(999999-111111 + 1) + 111111;
    	
    }
}
