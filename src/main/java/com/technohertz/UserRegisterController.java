package com.technohertz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.model.UserProfile;
import com.technohertz.model.UserRegister;
import com.technohertz.service.IUserRegisterService;

@RestController
@RequestMapping("/userRest")
public class UserRegisterController {
	
	@Autowired
	private IUserRegisterService userRegisterService;

//	@GetMapping("/employees")
//	public List<UserRegister> getAllEmployees() {
//		return userRegisterService.findAll();
//	}
//
//	@GetMapping("/employees/{id}")
//	public ResponseEntity<UserRegister> getEmployeeById(@PathVariable(value = "id") Long employeeId)
//			throws ResourceNotFoundException {
//		UserRegister employee = userRegisterService.findById(employeeId)
//				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//		return ResponseEntity.ok().body(employee);
//	}

	@PostMapping("/save")
	public UserRegister createEmployee(@Valid @RequestBody UserRegister user) {
		return userRegisterService.save(user);
	}
	
	@PostMapping("/saveall")
	public  void addUser(@Valid @RequestBody UserRegister userDetails ) {
		UserRegister user=new UserRegister();
		
		UserProfile profile=new UserProfile();
		
		user.setUserName(userDetails.getUserName());
		user.setPassword(userDetails.getPassword());
		user.setMobilNumber(userDetails.getMobilNumber());
		user.setIsActive(true);
		user.setSourceFrom("Laptop");
		user.setToken(3123);
		user.setCreateDate(getDate());
		user.setLastModifiedDate(getDate());
		profile.setDisplayName(userDetails.getUserName());
		user.setProfile(profile);
		userRegisterService.save(user);
	}
//
//
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

	private LocalDateTime getDate() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now();  
		   
		return now;
		
	}
}
