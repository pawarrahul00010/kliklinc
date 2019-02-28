package com.technohertz;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.util.TockenUtil;

@RestController
public class SimpleMailController {
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	TockenUtil tockenUtil;
	
	@RequestMapping("/sendMail")
	public String sendMail(@RequestParam("mailId") String mailId) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(mailId);
			helper.setText(" http://localhost:8080/userRest/validate/"+tockenUtil.getOTP());
			helper.setSubject("This is an Authentication mail from KLIKLINK please click on the link to verify your account");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

	@RequestMapping("/sendMailAtt")
	public String sendMailAttachment() throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message,true);
		try {
			helper.setTo("shri.waghmare3@gmail.com");
			helper.setText("Greetings :)\n Please find the attached docuemnt for your reference.");
			helper.setSubject("Mail From Spring Boot");
			ClassPathResource file = new ClassPathResource("document.PNG");
			helper.addAttachment("document.PNG", file);
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}

}
