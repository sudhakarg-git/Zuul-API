package com.belk.fraudValidation.mail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class BatchMailSender {

	@Autowired
	private JavaMailSender mailsender;

	@Autowired
	private Environment env;
	
	@Value("${app.mail.recipients}")
	private String[] deliveryemails;

	@Value("${app.mailalert}")
	private boolean mailAlert;
	
	public void sendEmail(String textMessage) {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String strDate = dateFormat.format(date);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(deliveryemails);
		

		msg.setSubject("Error Occured during Fraud Validation Process in " + env.getProperty("environment") + " On: "
				+ strDate);
		msg.setText(textMessage);

		if(mailAlert) {
			mailsender.send(msg);
		}

	}

}
