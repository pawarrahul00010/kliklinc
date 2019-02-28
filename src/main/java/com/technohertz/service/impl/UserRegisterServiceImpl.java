package com.technohertz.service.impl;



import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.technohertz.model.UserRegister;
import com.technohertz.repo.UserRegisterRepository;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.DateUtil;
import com.technohertz.util.TockenUtil;
import com.technohertz.vo.VoUserRegister;

@Service
public class UserRegisterServiceImpl implements IUserRegisterService {
	@Autowired
	DateUtil dateUtil;

	@Autowired
	TockenUtil tockenUtil;
	
	@Autowired
	UserRegisterRepository userRegisterRepository;

	
	@Autowired
	private JavaMailSender sender;

	
	public VoUserRegister convertToNoteEntity(VoUserRegister viewModel) {
		viewModel.setCreateDate(dateUtil.getDate());
		viewModel.setLastModifiedDate(dateUtil.getDate());
		viewModel.setToken(tockenUtil.getOTP());

		return viewModel;
	}
	public String sendMail(String mail)
	{
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		UserRegister userRegister = userRegisterRepository.findByEmail(mail);
		Integer token=userRegister.getToken();
		try {
			helper.setTo(mail);
			helper.setText("Wel-Come to KLIKLINC \n \n "+" http://localhost:8080/userRest/validate/"+token+"    \n This is an Authentication mail from KLIKLINK please click on the link to verify your account");
			helper.setSubject("KLIKLINC");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}
	public UserRegister authenticateUser(Integer token) {
		UserRegister userRegister=userRegisterRepository.findByToken(token);
		Integer dbToken=userRegister.getToken();
		if(dbToken.equals(token))
		{
			userRegister.setIsActive(true);
			return userRegister;
		}
		else
		{
			userRegister.setIsActive(false);
			return userRegister;
		}
	
	}

}
