package com.technohertz;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.model.UserRegister;
import com.technohertz.repo.UserRegisterRepository;
import com.technohertz.service.impl.UserRegisterServiceImpl;
import com.technohertz.vo.VoUserRegister;
import com.technohertz.voimpl.VoUserRegisterImpl;

@RestController
@RequestMapping("/userRest")
@CrossOrigin
public class UserRegisterController {
	@Autowired
	UserRegisterRepository userRegisterRepository;

	@Autowired
	UserRegisterServiceImpl userRegisterServiceImpl;
	
	@Autowired
	VoUserRegisterImpl voUserRegisterImpl;
	
	  @PostMapping
	    public UserRegister save(@RequestBody VoUserRegister voUserRegister, BindingResult bindingResult) {
	        if (bindingResult.hasErrors()) {
	            throw new ValidationException();
	        }

	        UserRegister user = this.voUserRegisterImpl.convertToNoteEntity(voUserRegister);
	        this.userRegisterRepository.save(user);
	        
	        userRegisterServiceImpl.sendMail(user.getEmail());
	        return user;
	    }
	  @GetMapping("/validate/{Token}")
	    public UserRegister validateUser(@PathVariable(value = "Token") String Token) {
		  Integer token=Integer.parseInt(Token);
		  UserRegister userRegister= userRegisterServiceImpl.authenticateUser(token);
		  this.userRegisterRepository.save(userRegister);
		  return userRegister;
	  }

}
