package com.technohertz;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class UserProfileController {
	/*
	 * @Autowired private UserProfileRepository userprofilerepo;
	 * 
	 * @PutMapping("/employees/{id}") public ResponseEntity<UserProfile>
	 * updateEmployee(@PathVariable(value = "id") UserRegister register,
	 * 
	 * @Valid @RequestBody UserProfile profileDetails) throws
	 * ResourceNotFoundException { UserProfile employee =
	 * userprofilerepo.findBydisplayName(displayName) .orElseThrow(() -> new
	 * ResourceNotFoundException("Employee not found for this id :: " +
	 * employeeId));
	 * 
	 * employee.setPrifilePic(profileDetails.getPrifilePic());
	 * employee.setAboutUser(profileDetails.getAboutUser());
	 * 
	 * final UserProfile updatedEmployee = userprofilerepo.save(employee); return
	 * ResponseEntity.ok(updatedEmployee); }
	 */

}
