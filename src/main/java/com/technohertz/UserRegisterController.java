package com.technohertz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.exception.ResourceNotFoundException;
import com.technohertz.model.Biometric;
import com.technohertz.model.UserOtp;
import com.technohertz.model.UserProfile;
import com.technohertz.model.UserRegister;
import com.technohertz.repo.UserOtpRepository;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.OtpUtil;

@RestController
@RequestMapping("/userRest")
public class UserRegisterController {

	@Autowired
	private IUserRegisterService userRegisterService;
	
	@Autowired

	private EntityManager entitymanager;

	private UserOtpRepository userOtpRepo;
	
	@Autowired
	private OtpUtil util;
	
	
	@GetMapping("/myprofile")
	public List<UserRegister> getAllEmployees() {
		return userRegisterService.getAll();
	}

	@GetMapping("/myprofile/{userId}")
	public Optional<UserRegister> getAllEmployees(@PathVariable(value = "userId") Integer userId) {
		return userRegisterService.getById(userId);
	}
	
//
	@GetMapping("/profile/{name}")
	public ResponseEntity<List<UserRegister>> getEmployeeById(@PathVariable(value = "name") String userName)
			throws ResourceNotFoundException {
		List<UserRegister> username = (List<UserRegister>) userRegisterService.findByUserName(userName);
		return ResponseEntity.ok().body(username);
	}

	
	@PostMapping("/login")
	public ResponseEntity<String> loginCredential(@Valid @RequestBody UserRegister userDetails)
			throws ResourceNotFoundException {
		UserRegister userRegister=null;
		List<UserRegister> userRegisterList = userRegisterService.findByUserName(userDetails.getUserName());
		/*get from database*/
		
		if(userRegisterList.isEmpty())
		{
			return ResponseEntity.ok("Please check username");
		}
		else {
			userRegister = userRegisterList.get(0);
	
		String name = userRegister.getUserName();
		String password = userRegister.getPassword();
		Boolean userStatus=userRegister.getIsActive();
		/*get from body*/
		
		String uname = userDetails.getUserName();
		String pass = userDetails.getPassword();
		
		if (name.equals(uname) && password.equals(pass) && userStatus==true) 

			return ResponseEntity.ok("user Logged in successfully");
		 else
			return ResponseEntity.ok("Please check username or password");
		}
	}


	@PostMapping("/saveall")
	public ResponseEntity<String> addUser(@Valid @RequestBody UserRegister userDetails) {
		UserRegister user = new UserRegister();

		UserProfile profile = new UserProfile();
		
		Biometric biometric=new Biometric();

		user.setUserName(userDetails.getUserName());
		user.setPassword(userDetails.getPassword());
		user.setMobilNumber(userDetails.getMobilNumber());
		user.setIsActive(true);
		user.setSourceFrom("Laptop");
		user.setToken(getRandomNumber());
		user.setCreateDate(getDate());
		user.setLastModifiedDate(getDate());
		profile.setDisplayName(userDetails.getUserName());
		biometric.setIsActive(true);
		user.setProfile(profile);
		user.getBiometric().add(biometric);	
		UserOtp userOtp = new UserOtp();
		if(!userExists(userDetails.getUserName())) {
			userOtp.setIs_active(true);
			userOtp.setCreateDate(getDate());
			userOtp.setLastModifiedDate(getDate());
			int OTP = util.getOTP();
			userOtp.setOtp(OTP);
			user.setUserOtp(userOtp);

			userRegisterService.save(user);

			return  ResponseEntity.ok("User Saved successfully and your OTP is : "+OTP);	

		}
		else {
			
			return  ResponseEntity.ok("User Allready Exist. . .!!!");
		}
	
	}
		


	private boolean userNotExists(String userName) {
		boolean userNotExist = false;
		
		List<UserRegister> UserList =(List<UserRegister>) userRegisterService.findByUserName(userName);
		
		if(UserList.isEmpty()) {
			
			userNotExist = true;
		}
		return userNotExist;
	}
	private boolean userExists(String userName)
	{
		String hql="FROM UserRegister as ur WHERE ur.userName= ?1";
		int count=entitymanager.createQuery(hql).setParameter(1, userName).getResultList().size();
		
		return count>0 ? true : false;
		
	}

	private LocalDateTime getDate() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		return now;

	}

	private int getRandomNumber() {

		int rand = new Random().nextInt(10000); 
		  
		return rand;

	}
}
