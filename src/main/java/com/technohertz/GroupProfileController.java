package com.technohertz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityManager;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.technohertz.common.Constant;
import com.technohertz.exception.ResourceNotFoundException;
import com.technohertz.model.Biometric;
import com.technohertz.model.GroupProfile;
import com.technohertz.model.LikedUsers;
import com.technohertz.model.MediaFiles;
import com.technohertz.model.UserContact;
import com.technohertz.model.UserOtp;
import com.technohertz.model.UserProfile;
import com.technohertz.model.UserRegister;
import com.technohertz.payload.UploadFileResponse;
import com.technohertz.repo.GroupProfileRepository;
import com.technohertz.repo.MediaFileRepo;
import com.technohertz.repo.UserProfileRepository;
import com.technohertz.repo.UserRegisterRepository;
import com.technohertz.service.impl.FileStorageService;
import com.technohertz.util.ResponseObject;


@RestController
@RequestMapping("/group")
public class GroupProfileController {
	@Autowired
	private GroupProfileRepository groupProfileRepository;

	@Autowired
	private EntityManager entitymanager;

	@Autowired
	private UserRegisterRepository registerRepository;

	@Autowired
	private MediaFileRepo mediaFileRepo;

	@Autowired
	private ResponseObject response;
	
	@Autowired
	private FileStorageService fileStorageService;

	private static String UPLOADED_FOLDER = "F://temp//";
	@SuppressWarnings("unused")
	@PostMapping("/create")
	public ResponseEntity<ResponseObject> createGroup(@RequestBody UserContact userContacts) {

		if(userContacts.equals("") && userContacts == null) {
			
			response.setError("1");
			response.setMessage("wrong userName please enter correct value");
			response.setData("[]");
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);
		
		}else {
		
				UserRegister user = new UserRegister();
		
				UserProfile profile = new UserProfile();
				//MediaFiles mediaFiles=new MediaFiles();
				Biometric biometric=new Biometric();
		
				
				if(!userExists(userContacts.getContactNumber()) ){
					
					GroupProfile groupProfile=new GroupProfile();
					groupProfile.getGroupMember().add(userContacts);
					userContacts.setCreateDate(getDate());
					userContacts.setContactNumber(userContacts.getContactNumber());
					groupProfileRepository.save(groupProfile);
					
					response.setStatus("Success");
					response.setMessage("  CraziApp Registration is successful");
					response.setError("");
					response.setData(user);
					
					return ResponseEntity.ok(response);
					
				}
				else {
		
					response.setStatus("FAIL");
					response.setMessage(" user is allready Registered");
					response.setError("1");
					response.setData("[]");
						return ResponseEntity.ok(response);
				}
			}
		}
	private boolean userExists(Long mobileNumber)
	{
		String hql="FROM UserContact as ur WHERE ur.contactNumber= ?1";
		int count=entitymanager.createQuery(hql).setParameter(1, mobileNumber).getResultList().size();
		
		return count>0 ? true : false;
		
	}


	@SuppressWarnings("unused")
	@PutMapping("/displayName/{id}")
	public ResponseEntity<ResponseObject> updateDisplayName(@RequestParam("displayName") String displayName,
			@PathVariable(value = "id") String profileid) throws ResourceNotFoundException {


		if(displayName.equals("") && displayName == null && profileid.equals("") && profileid == null) {

			response.setError("1");
			response.setMessage(" please enter correct value");
			response.setData("[]");
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		}
		else {

			int id = 0;
			try {
				id = Integer.parseInt(profileid);
			} catch (NumberFormatException e) {

				response.setError("1");
				response.setMessage("wrong profileId please enter numeric value");
				response.setData("[]");
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);

			}

			List<GroupProfile> profile = null;
			GroupProfile updateDisplayName =null;

			profile = groupProfileRepository.findById(id);
			if(!profile.isEmpty()) {

				profile.get(0).setDisplayName(displayName);
				profile.get(0).setProfileId(id);
				updateDisplayName = groupProfileRepository.save(profile.get(0));

				response.setMessage("your Display name updated successfully");
				response.setData(updateDisplayName);
				response.setError("");
				response.setStatus("success");

				return ResponseEntity.ok(response);	
			}
			else {
				response.setMessage("user not available");
				response.setData("[]");
				response.setError("1");
				response.setStatus("fail");
				return ResponseEntity.ok(response);
			}
		}
	}


	@PostMapping("/likes/{userId}")
	public ResponseEntity<ResponseObject> totalLikes(@RequestParam("fileid") int fileid,@RequestParam("isLiked") boolean isLiked,
			@PathVariable(value = "userId") int  userId) {
		MediaFiles mediaFiles= mediaFileRepo.getById(fileid);
		UserRegister userRegister =registerRepository.getOne(userId);
		LikedUsers likedUsers=new LikedUsers();
		likedUsers.setUserName(userRegister.getUserName());
		likedUsers.setMarkType(Constant.LIKE);
		mediaFiles.getLikedUsers().add(likedUsers); 
		long count=0;

		if(mediaFiles.getLikes() == null) {
			count=0;
		} else{
			count=mediaFiles.getLikes();
		}
		if(isLiked==true) {

			count = count+1;
			mediaFiles.setLikes(count);
			mediaFileRepo.save(mediaFiles);

			response.setError("0");
			response.setMessage("user liked successfully");
			response.setData(mediaFiles);
			response.setStatus("SUCCESS");
			return ResponseEntity.ok(response);

		}
		else {

			count = count-1;
			mediaFiles.setLikes(count);
			mediaFileRepo.save(mediaFiles);

			response.setError("1");
			response.setMessage("user unliked successfully");
			response.setData("[]");
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		}

	}
	
	@SuppressWarnings("unused")
	@PostMapping("/rating/{userId}")
	public ResponseEntity<ResponseObject> totalRating(@RequestParam("fileid") String userfileid,
			@RequestParam("isRated") String isRated,@RequestParam("rateCount") String rateCounts,
			@PathVariable(value = "userId") int  userId) {

		if(userfileid.equals("") && userfileid == null && isRated.equals("") && isRated == null && rateCounts.equals("") && rateCounts == null) {

			response.setError("1");
			response.setMessage("wrong fileid, rateCount and isRated please enter correct value");
			response.setData("[]");
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		}
		else {

			int fileid = 0;
			int rateCount = 0;
			boolean isRate = false;
			try {
				isRate = Boolean.parseBoolean(isRated);
				fileid = Integer.parseInt(userfileid);
				rateCount = Integer.parseInt(rateCounts);
			} catch (Exception e) {

				response.setError("1");
				response.setMessage("wrong fileid and rateCount please enter numeric value");
				response.setData("[]");
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);

			}

			MediaFiles mediaFiles= mediaFileRepo.getById(fileid);

			UserRegister userRegister =registerRepository.getOne(userId);
			LikedUsers likedUsers=new LikedUsers();
			likedUsers.setUserName(userRegister.getUserName());
			likedUsers.setMarkType(Constant.RATE);
			mediaFiles.getLikedUsers().add(likedUsers); 

			//Long totalLikes=mediaFiles.getLikes();
			long rate=0;
			System.out.println(mediaFiles.getLikes());
			if(mediaFiles.getRating() == null) {

				rate=0;

			} else{

				rate=mediaFiles.getRating();
			}

			if(isRate==true) {
				rate = rate+rateCount;
				mediaFiles.setRating(rate);
				mediaFileRepo.save(mediaFiles);

				response.setError("0");
				response.setMessage("user rated with : "+rateCount);
				response.setData(mediaFiles);
				response.setStatus("SUCCESS");
				return ResponseEntity.ok(response);


			}
			else {

				mediaFiles.setRating(rate);
				mediaFileRepo.save(mediaFiles);
				response.setError("1");
				response.setMessage("rating on image is not done");
				response.setData("[]");
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);
			}
		}
	}

	@SuppressWarnings("unused")
	@PutMapping("/aboutUs/{id}")
	public ResponseEntity<ResponseObject> updateStatus(@RequestParam("aboutUs") String aboutUs,
			@PathVariable(value = "id") String userid) throws ResourceNotFoundException {

		if(aboutUs.equals("") && aboutUs == null && userid.equals("") && userid == null ) {

			response.setError("1");
			response.setMessage("wrong aboutUs and userid please enter correct value");
			response.setData("[]");
			response.setStatus("FAIL");
			return ResponseEntity.ok(response);

		}
		else {

			int id = 0;
			int rateCount = 0;
			boolean isRated = false;
			try {
				id = Integer.parseInt(userid);
			} catch (NumberFormatException e) {

				response.setError("1");
				response.setMessage("wrong fileid and rateCount please enter numeric value");
				response.setData("[]");
				response.setStatus("FAIL");
				return ResponseEntity.ok(response);

			}

			List<GroupProfile> profile = null;
			GroupProfile updateDisplayName =null;


			profile = groupProfileRepository.findById(id);
			if(!profile.isEmpty()) {
				profile.get(0).setAboutUser(aboutUs);
				profile.get(0).setProfileId(id);
				updateDisplayName = groupProfileRepository.save(profile.get(0));

				response.setMessage("your status updated successfully");
				response.setData(updateDisplayName);
				response.setError("");
				response.setStatus("success");

				return ResponseEntity.ok(response);	

			}
			else {

				response.setMessage("user not available");
				response.setData("[]");
				response.setError("1");
				response.setStatus("fail");
				return ResponseEntity.ok(response);
			}
		}
	}


	@PutMapping("/uploadFile/{userId}")
	public UploadFileResponse updateProfile(@RequestParam("file") MultipartFile file,@PathVariable(value = "userId") int userId) {
		GroupProfile groupProfile = fileStorageService.savegroupProfile(file,userId);
		MediaFiles files=mediaFileRepo.getOne(Integer.valueOf(String.valueOf(groupProfile.getFiles().get(groupProfile.getFiles().size()-1).getFileId())));
		System.out.println(files);
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(String.valueOf(files.getFilePath()))
				.toUriString();

		return new UploadFileResponse(groupProfile.getCurrentProfile(), fileDownloadUri,
				file.getContentType(), file.getSize());
	}

	@PostMapping("/upload") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}

		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/uploadStatus";
	}

	private LocalDateTime getDate() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();

		return now;

	}

	private String getFileExtension(MultipartFile file) {
	    String name = file.getOriginalFilename();
	    int lastIndexOf = name.lastIndexOf(".");
	    if (lastIndexOf == -1) {
	        return ""; // empty extension
	    }
	    return name.substring(lastIndexOf);
	}
}

