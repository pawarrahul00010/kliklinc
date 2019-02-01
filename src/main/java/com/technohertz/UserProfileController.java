package com.technohertz;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.technohertz.exception.ResourceNotFoundException;
import com.technohertz.model.MediaFiles;
import com.technohertz.model.UserProfile;
import com.technohertz.payload.UploadFileResponse;
import com.technohertz.repo.UserProfileRepository;
import com.technohertz.service.impl.FileStorageService;



@RestController
@RequestMapping("/profile")
public class UserProfileController {
	@Autowired
	private UserProfileRepository userprofilerepo;
	
	  @Autowired
	  private FileStorageService fileStorageService;
	
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
	@PutMapping("/user/displayName/{id}")
	public ResponseEntity<UserProfile> updateDisplayName(@RequestParam("displayName") String displayName,@PathVariable(value = "id") int  id) throws ResourceNotFoundException {
		UserProfile userProfile = new UserProfile();
		if(id!=0) {
			Optional<UserProfile> profile =  userprofilerepo.findById(id);
		}
		else {
			System.out.println("please give name");
		}
		userProfile.setDisplayName(displayName);
		userProfile.setProfileId(id);
		final UserProfile updateDisplayName = userprofilerepo.save(userProfile);
		return ResponseEntity.ok(updateDisplayName);
	}
	
    @PutMapping("/uploadFile/{id}")
    public UploadFileResponse updateProfile(@RequestParam("file") MultipartFile file,@PathVariable(value = "id") int id) {
    	MediaFiles fileName = fileStorageService.saveProfile(file,id);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName.getFilePath())
                .toUriString();
      
		return new UploadFileResponse(fileName.getFilePath(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }
	
    
 
	private LocalDateTime getDate() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		return now;

	}

}
