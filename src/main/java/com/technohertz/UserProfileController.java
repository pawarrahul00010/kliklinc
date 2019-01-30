package com.technohertz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.technohertz.exception.ResourceNotFoundException;
import com.technohertz.model.UserProfile;
import com.technohertz.repo.UserProfileRepository;



@RestController
@RequestMapping("/profile")
public class UserProfileController {
	@Autowired
	private UserProfileRepository userprofilerepo;
	@PutMapping("/user/{name}")
	public ResponseEntity<UserProfile> updateUser(@PathVariable(value = "name") String displayName,
			@Valid @RequestBody UserProfile profileDetails) throws ResourceNotFoundException {
		UserProfile profile = (UserProfile) userprofilerepo.findBydisplayName(displayName);
		
//		MediaFiles mediaFiles=new MediaFiles();
		
		String name=profileDetails.getDisplayName();
		
		String aboutUser=profileDetails.getAboutUser();
		if(name!=null)
		{
			profile.setDisplayName(name);
		}
		else {
			profile.setDisplayName(profile.getDisplayName());
		}

		if(aboutUser!=null)
		{
			profile.setAboutUser(aboutUser);
		}else {
		profile.setAboutUser(profile.getAboutUser());
		}
//		if(profilePic!=null)
//		{
//			profile.setPrifilePic(profilePic);
//		}
//		else {
//		profile.setPrifilePic(profile.getPrifilePic());
//		}
		
//		mediaFiles.setFilePath("abc/bac");
//		mediaFiles.setIsBookMarked(true);
//		mediaFiles.setCreateDate(getDate());
//		mediaFiles.setLastModifiedDate(getDate());
//		mediaFiles.setIsLiked(true);
//		mediaFiles.setIsShared(true);
//		mediaFiles.setLikes(12);
//		mediaFiles.setRating(12);
//		profile.getFiles().add(mediaFiles);
//		
//		
		
		final UserProfile updatedUser = userprofilerepo.save(profile);
		return ResponseEntity.ok(updatedUser);
	}
	@PutMapping("/user/displayName/{displayName}")
	public ResponseEntity<UserProfile> updateDisplayName(@PathVariable(value = "displayName") String displayName,
			@Valid @RequestBody UserProfile profileDetails) throws ResourceNotFoundException {
		UserProfile profile=null;
		if(displayName!=null) {
		 profile = (UserProfile) userprofilerepo.findBydisplayName(displayName);
		}
		else {
			System.out.println("please give name");
		}
		profile.setDisplayName(profileDetails.getDisplayName());		
		final UserProfile updateDisplayName = userprofilerepo.save(profile);
		return ResponseEntity.ok(updateDisplayName);
	}
	private LocalDateTime getDate() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		return now;

	}

}
