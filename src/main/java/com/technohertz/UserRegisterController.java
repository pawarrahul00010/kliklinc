package com.technohertz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

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
	private UserOtpRepository userOtpRepo;
	
	@Autowired
	private OtpUtil util;
	

	@GetMapping("/myprofile")
	public List<UserRegister> getAllEmployees() {
		return userRegisterService.getAll();
	}
	
//
	@GetMapping("/profile/{name}")
	public ResponseEntity<List<UserRegister>> getEmployeeById(@PathVariable(value = "name") String userName)
			throws ResourceNotFoundException {
		List<UserRegister> username = (List<UserRegister>) userRegisterService.findByUserName(userName);
		return ResponseEntity.ok().body(username);
	}

	@GetMapping("/login/{userName}/{password}")
	public ResponseEntity<List<UserRegister>> loginCredential(@PathVariable(value = "userName") String userName,
			@PathVariable(value = "password") String password) throws ResourceNotFoundException {

		List<UserRegister> user = userRegisterService.findByUserNameAndPassword(userName, password);
		System.out.println(userName);
		if (userName.equals(userName) && password.equals(password)) {

			return ResponseEntity.ok(user);
		} else
			return ResponseEntity.ok(user);

		
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
		biometric.setRegister(user);
		user.getFiles().add(biometric);
		user.setProfile(profile);
		UserOtp userOtp = new UserOtp();
		if(!userExists(userDetails.getUserName()).contains(user.getUserName())) {
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
			System.out.println("User Allready Exist");
			return  ResponseEntity.ok("User Allready Exist");
		}
	
	}

//	@PutMapping("/employees/{id}")
//	public ResponseEntity<UserRegister> updateEmployee(@PathVariable(value = "id") Long employeeId,
//			@Valid @RequestBody UserRegister employeeDetails) throws ResourceNotFoundException {
//		UserRegister employee = userRegisterService.findById(employeeId)
//				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//
//		employee.setEmailId(employeeDetails.getEmailId());
//		employee.setLastName(employeeDetails.getLastName());
//		employee.setFirstName(employeeDetails.getFirstName());
//		final Employee updatedEmployee = userRegisterService.save(employee);
//		return ResponseEntity.ok(updatedEmployee);
//	}
//
//	@DeleteMapping("/employees/{id}")
//	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
//			throws ResourceNotFoundException {
//		Employee employee = userRegisterService.findById(employeeId)
//				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//
//		userRegisterService.delete(employee);
//		Map<String, Boolean> response = new HashMap<>();
//		response.put("deleted", Boolean.TRUE);
//		return response;
//	}

	private List<UserRegister> userExists(String userName) {
		List<UserRegister> username = (List<UserRegister>) userRegisterService.findByUserName(userName);
		return username;

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
