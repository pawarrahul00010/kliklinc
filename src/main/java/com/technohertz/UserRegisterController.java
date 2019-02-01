package com.technohertz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.exception.ResourceNotFoundException;
import com.technohertz.model.Biometric;
import com.technohertz.model.ExceptionHandle;
import com.technohertz.model.MediaFiles;
import com.technohertz.model.UserOtp;
import com.technohertz.model.UserProfile;
import com.technohertz.model.UserRegister;
import com.technohertz.service.IUserRegisterService;
import com.technohertz.util.OtpUtil;
import com.technohertz.util.sendSMS;

@RestController
@RequestMapping("/userRest")
public class UserRegisterController {

	@Autowired
	private IUserRegisterService userRegisterService;
	
	@Autowired

	private EntityManager entitymanager;

	@Autowired
	private OtpUtil util;
	
	@Autowired
	private sendSMS sms;
	
	
	@GetMapping("/myprofile")
	public List<UserRegister> getAllEmployees() {
		return userRegisterService.getAll();
	}

	@GetMapping("/myprofile/{userId}")
	public ResponseEntity<ExceptionHandle> getAllEmployees(@PathVariable(value = "userId") Integer userId) {
		ExceptionHandle exceptionHandle =new ExceptionHandle();
	
		List<UserRegister> register = userRegisterService.getById(userId);
	if(!register.isEmpty()) {
	exceptionHandle.setMassage("your data is retrived successfully");
	exceptionHandle.setObject(register);
	exceptionHandle.setError_code("false");
	exceptionHandle.setStatus("success");
	return ResponseEntity.ok(exceptionHandle);	
	}
	else {
		exceptionHandle.setMassage("no data found");
		exceptionHandle.setObject("[]");
		exceptionHandle.setError_code("true");
		exceptionHandle.setStatus("fail");
		return ResponseEntity.ok(exceptionHandle);		
	}
	
	}
	
//
	@GetMapping("/profile/{name}")
	public ResponseEntity<List<UserRegister>> getEmployeeById(@PathVariable(value = "name") String userName)
			throws ResourceNotFoundException {
		List<UserRegister> username = (List<UserRegister>) userRegisterService.findByUserName(userName);
		return ResponseEntity.ok().body(username);
	}

	
	@PostMapping("/login")
	public ResponseEntity<ExceptionHandle> loginCredential(@RequestParam String userName,@RequestParam String password)
			throws ResourceNotFoundException {
		UserRegister userRegister=null;
		List<UserRegister> userRegisterList = userRegisterService.findByUserName(userName);
		/*get from database*/
		ExceptionHandle exceptionHandle = new ExceptionHandle();
		if(userRegisterList.isEmpty())
		{
			exceptionHandle.setMassage("no user found please do entry");
			exceptionHandle.setError_code("1");
			exceptionHandle.setStatus("fail");
			exceptionHandle.setObject(userRegisterList);
			return ResponseEntity.ok(exceptionHandle);
		}
		else {
			userRegister = userRegisterList.get(0);
	
		String name = userRegister.getUserName();
		String dbpassword = userRegister.getPassword();
		Boolean userStatus=userRegister.getIsActive();

		
		if (name.equals(userName) && password.equals(dbpassword) && userStatus==true) 
		{
			exceptionHandle.setStatus("success");
		exceptionHandle.setMassage("Logged in successfully");
		exceptionHandle.setError_code("false");
		exceptionHandle.setObject(userRegisterList);
			return ResponseEntity.ok(exceptionHandle);
		}else {
			 exceptionHandle.setStatus("false");
		exceptionHandle.setMassage("please check username or password");
		exceptionHandle.setError_code("1");
		exceptionHandle.setObject("[]");
			return ResponseEntity.ok(exceptionHandle);
		}
	}
}


	@PostMapping("/saveall")
	public ResponseEntity<ExceptionHandle> addUser(@RequestParam String userName,@RequestParam String password,@RequestParam String mobileNumber) {
		UserRegister user = new UserRegister();

		ExceptionHandle exceptionHandle=new ExceptionHandle();
		UserProfile profile = new UserProfile();
		MediaFiles mediaFiles=new MediaFiles();
		Biometric biometric=new Biometric();

		user.setUserName(userName);
		user.setPassword(password);
		user.setMobilNumber(Long.parseLong(mobileNumber));
		user.setIsActive(true);
		user.setSourceFrom("Laptop");
		user.setToken(getRandomNumber());
		user.setCreateDate(getDate());
		user.setLastModifiedDate(getDate());
		profile.setDisplayName(user.getUserName());
		
		profile.getFiles().add(mediaFiles);
		biometric.setIsActive(true);
		user.setProfile(profile);
		user.getBiometric().add(biometric);	
		UserOtp userOtp = new UserOtp();
		if(!userExists(userName) ){
			userOtp.setIs_active(true);
			userOtp.setCreateDate(getDate());
			userOtp.setLastModifiedDate(getDate());
			int OTP = util.getOTP();
			userOtp.setOtp(OTP);
			user.setUserOtp(userOtp);

			userRegisterService.save(user);
			sms.sendSms(String.valueOf(mobileNumber), "Your CraziApp Registration is successful enter OTP to verify : "+OTP);
			
			exceptionHandle.setStatus("success");
			exceptionHandle.setMassage("  CraziApp Registration is successful");
			exceptionHandle.setError_code("false");
			exceptionHandle.setObject(user);
				return ResponseEntity.ok(exceptionHandle);
			
		}
		else {

			exceptionHandle.setStatus("fail");
			exceptionHandle.setMassage(" user is allready Registered");
			exceptionHandle.setError_code("1");
			exceptionHandle.setObject("[]");
				return ResponseEntity.ok(exceptionHandle);
		}
	
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
