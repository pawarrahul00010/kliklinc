package com.technohertz;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.technohertz.common.Constant;
import com.technohertz.exception.ResourceNotFoundException;
import com.technohertz.model.LikedUsers;
import com.technohertz.model.MediaFiles;
import com.technohertz.model.UserProfile;
import com.technohertz.model.UserRegister;
import com.technohertz.payload.UploadFileResponse;
import com.technohertz.repo.MediaFileRepo;
import com.technohertz.repo.UserProfileRepository;
import com.technohertz.repo.UserRegisterRepository;
import com.technohertz.service.impl.FileStorageService;
import com.technohertz.util.ResponseObject;


@RestController
@RequestMapping("/profile")
public class UserProfileController {
	@Autowired
	private UserProfileRepository userprofilerepo;

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
	@PostMapping("/displayName/{id}")
	public ResponseEntity<ResponseObject> updateDisplayName(@RequestParam("displayName") String displayName,
			@PathVariable(value = "id") String profileid) throws ResourceNotFoundException {


		if(displayName.equals("") && displayName == null && profileid.equals("") && profileid == null) {

			response.setError("1");
			response.setMessage("wrong username and profileid please enter correct value");
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

			List<UserProfile> profile = null;
			UserProfile updateDisplayName =null;

			profile = userprofilerepo.findById(id);
			if(!profile.isEmpty()) {

				profile.get(0).setDisplayName(displayName);
				profile.get(0).setProfileId(id);
				updateDisplayName = userprofilerepo.save(profile.get(0));

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
		likedUsers.setFileID(fileid);
		mediaFiles.getLikedUsers().add(likedUsers); 
		long count=0;

		if(mediaFiles.getLikes() == null) {
			count=0;
		} else{
			count=mediaFiles.getLikes();
		}
		if(isLiked==true && mediaFiles.getIsLiked()==false ) {
		count = count+1;
			mediaFiles.setLikes(count);
			mediaFiles.setIsLiked(isLiked);
			mediaFileRepo.save(mediaFiles);

			response.setError("0");
			response.setMessage("user liked successfully");
			response.setData(mediaFiles);
			response.setStatus("SUCCESS");
			return ResponseEntity.ok(response);

		}
		else {
	
		long totalcount=	mediaFiles.getLikes();
		count = totalcount-1;
			mediaFiles.setLikes(count);
			if(count>=0) {
			mediaFiles.setIsLiked(false);
			likedUsers.setMarkType("UNLIKED");
			mediaFileRepo.save(mediaFiles);
			}
			response.setError("0");
			response.setMessage("user unliked successfully");
			response.setData(mediaFiles);
			response.setStatus("SUCCESS");
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
			likedUsers.setFileID(fileid);
			mediaFiles.getLikedUsers().add(likedUsers); 

			//Long totalLikes=mediaFiles.getLikes();
			long rate=0;
			System.out.println(mediaFiles.getLikes());
			if(mediaFiles.getRating() == null) {

				rate=0;

			} else{

				rate=mediaFiles.getRating();
			}


			if(isRate==true&&  mediaFiles.getIsRated()==false ) {

			
				rate = rate+rateCount;
				mediaFiles.setRating(rate);
				mediaFiles.setIsRated(isRate);
				mediaFileRepo.save(mediaFiles);

				response.setError("0");
				response.setMessage("user rated with : "+rateCount);
				response.setData(mediaFiles);
				response.setStatus("SUCCESS");
				return ResponseEntity.ok(response);


			}
			else {

				long totalcount=	mediaFiles.getRating();
				rate = totalcount-Long.parseLong(rateCounts);
					mediaFiles.setLikes(rate);
					if(rate>=0) {
					mediaFiles.setIsRated(false);
					likedUsers.setMarkType(Constant.RATE);
					likedUsers.setFileID(fileid);
					mediaFiles.setRating(rate);
				    mediaFileRepo.save(mediaFiles);
			}


				mediaFiles.setRating(rate);
				mediaFileRepo.save(mediaFiles);

				response.setError("0");
				response.setMessage("rating updated");
				response.setData(mediaFiles);
				response.setStatus("SUCCESS");
				return ResponseEntity.ok(response);
			}
		}
	}

	@SuppressWarnings("unused")
	@PostMapping("/aboutUs/{id}")
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

			List<UserProfile> profile = null;
			UserProfile updateDisplayName =null;


			profile = userprofilerepo.findById(id);
			if(!profile.isEmpty()) {
				profile.get(0).setAboutUser(aboutUs);
				profile.get(0).setProfileId(id);
				updateDisplayName = userprofilerepo.save(profile.get(0));

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
    @PostMapping("/profile/{userId}")
    public  ResponseEntity<ResponseObject> saveProfile(@RequestParam("file") MultipartFile file,@RequestParam("DisplayName") String DisplayName,
    		@PathVariable(value = "userId") Integer  userId) {
     
    	UserProfile userProfile = fileStorageService.saveAllProfile(file,userId,DisplayName);
    	MediaFiles files=mediaFileRepo.getOne(Integer.valueOf(String.valueOf(userProfile.getFiles().get(userProfile.getFiles().size()-1).getFileId())));
       
       Object obj=new UploadFileResponse(files.getFilePath(), userProfile.getCurrentProfile(),
                file.getContentType(), file.getSize());
       if (!file.isEmpty()||userId!=null) {
			response.setMessage("your Profile Image updated successfully");

			response.setData(obj);
			response.setError("0");
			response.setStatus("SUCCESS");

			return ResponseEntity.ok(response);
		} else {
			response.setMessage("your Profile Image not updated");

			response.setData("[]");
			response.setError("1");
			response.setStatus("FAIL");

			return ResponseEntity.ok(response);
		}
    }

    @PostMapping("/uploadFile/{userId}")
	public ResponseEntity<ResponseObject> updateProfile(@RequestParam("file") MultipartFile file,@PathVariable(value = "userId") Integer userId) {
		UserProfile userProfile = fileStorageService.saveProfile(file,userId);
		MediaFiles files=mediaFileRepo.getOne(Integer.valueOf(String.valueOf(userProfile.getFiles().get(userProfile.getFiles().size()-1).getFileId())));
		
	

		Object obj= new UploadFileResponse(userProfile.getCurrentProfile(),userProfile.getCurrentProfile(),
				file.getContentType(), file.getSize());
		if (!file.isEmpty()||userId!=null) {
			response.setMessage("your Profile Image updated successfully");

			response.setData(obj);
			response.setError("0");
			response.setStatus("SUCCESS");

			return ResponseEntity.ok(response);
		} else {
			response.setMessage("your Profile Image not updated");

			response.setData("[]");
			response.setError("1");
			response.setStatus("FAIL");

			return ResponseEntity.ok(response);
		}
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







}

